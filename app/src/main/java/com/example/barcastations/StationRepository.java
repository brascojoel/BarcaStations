package com.example.barcastations;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StationRepository {
    private StationDao stationDao;
    private LiveData<List<Station>> allStations;

    public StationRepository(Application application){
        StationDatabase db = StationDatabase.getInstance(application);
        stationDao = db.stationDao();
        allStations = stationDao.getAllStation();
    }

    public void insert(Station station){

        new InsertAsyncTask(stationDao).execute(station);

    }

    public  void update(Station station){
        new UpdateAsyncTask(stationDao).execute(station);
    }

    public void delete(Station station){
        new DeleteAsyncTask(stationDao).execute(station);
    }


    public Station getStation(String id){
        Station station = null;
        try {
           station = new GetStationByIdAsyncTask(stationDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return station;
    }

    public LiveData<List<Station>> getAllStations(){
        return allStations;
    }


    private static class InsertAsyncTask extends AsyncTask<Station, Void, Void> {

        private StationDao stationDao;

        public InsertAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;
        }
        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.insert(stations[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Station ,Void,Void>{

        private StationDao stationDao;

        public UpdateAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;
        }
        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.update(stations[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Station ,Void,Void>{

        private StationDao stationDao;

        public DeleteAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;
        }
        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.delete(stations[0]);
            return  null;
        }
    }

    private static class GetStationByIdAsyncTask extends AsyncTask<String,Void,Station>{


        private StationDao stationDao;

        public GetStationByIdAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;

        }
        @Override
        protected Station doInBackground(String... strings) {
            return stationDao.getStation(strings[0]);
        }

        @Override
        protected void onPostExecute(Station station) {

        }
    }
}
