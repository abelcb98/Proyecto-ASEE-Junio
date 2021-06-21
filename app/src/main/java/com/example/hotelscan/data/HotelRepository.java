package com.example.hotelscan.data;

import android.util.Log;
import android.widget.ProgressBar;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.data.network.HotelNetworkDataSource;
import com.example.hotelscan.data.roomdb.HotelDao;
import com.example.hotelscan.data.roomdb.HotelDB;

import java.util.Arrays;
import java.util.List;

public class HotelRepository {

    private final String LOG_TAG="FlyScan";
    private final MutableLiveData<HotelDB> userFilterLiveData = new MutableLiveData<>();
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private static HotelRepository sInstance;
    private final HotelDao mHotelDao;
    private final HotelNetworkDataSource mHotelNetworkDataSource;

    public HotelRepository(HotelDao mHotelDao, HotelNetworkDataSource mHotelNetworkDataSource) {
        this.mHotelDao = mHotelDao;
        this.mHotelNetworkDataSource = mHotelNetworkDataSource;
        LiveData<HotelDB[]> networkData = mHotelNetworkDataSource.getCurrentRepos();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newHotelFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newHotelFromNetwork.length > 0){
                    mHotelDao.deleteHotelBySearch(newHotelFromNetwork[0].getCiudad());
                }
                // Insert our new repos into local database
                mHotelDao.bulkInsert(Arrays.asList(newHotelFromNetwork));
                //Log.d(LOG_TAG, "New values inserted in Room");
            });
        });


    }

    public synchronized static HotelRepository getInstance(HotelDao dao, HotelNetworkDataSource nds) {
        if (sInstance == null) {
            sInstance = new HotelRepository(dao, nds);
        }
        return sInstance;
    }

    public void setHotel(HotelDB h, ProgressBar p){
        // TODO - Set value to MutableLiveData in order to filter getCurrentRepos LiveData
        userFilterLiveData.setValue(h);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (mHotelDao.getNumberHotel(h.getFecha_inicio(),h.getFecha_fin(),h.getNum_personas(),h.getCiudad())==0) {
                doFetchHotel(h,p);
            }
        });
    }

    public void doFetchHotel(HotelDB h,ProgressBar p){
        AppExecutors.getInstance().diskIO().execute(() -> {
           int delete= mHotelDao.deleteHotelBySearch(h.getCiudad());
           Log.d("FlyScan","Hoteles borrados: "+delete);
            mHotelNetworkDataSource.fetchHotel(h.getFecha_inicio(),h.getFecha_fin(),h.getNum_personas(),h.getCiudad(),p);
        });
    }

    /**
     * Database related operations
     **/

    public LiveData<List<HotelDB>> getCurrentHotel() {
        // Return LiveData from Room. Use Transformation to get owner
        Log.d(LOG_TAG, "");
        return Transformations.switchMap(userFilterLiveData, new Function<HotelDB, LiveData<List<HotelDB>>>() {
            @Override
            public LiveData<List<HotelDB>> apply(HotelDB h) {
                return mHotelDao.getHotelBySearch(h.getFecha_inicio(),h.getFecha_fin(),h.getNum_personas(),h.getCiudad());
            }
        });
    }


    public LiveData<List<HotelDB>> getAllCurrentHotel() {
        return mHotelDao.getRecentHotel();

    }



}
