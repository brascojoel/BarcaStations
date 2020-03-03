package com.example.barcastations;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureRepository {
    private PictureDao pictureDao;
    private LiveData<List<Picture>> allPictures;

    public PictureRepository(Application application){
        StationDatabase db = StationDatabase.getInstance(application);
        pictureDao = db.pictureDao();

    }

    public void insert(Picture picture){

        new InsertAsyncTask(pictureDao).execute(picture);

    }

    public  void update(Picture picture){
         new UpdateAsyncTask(pictureDao).execute(picture);
    }

    public void delete(Picture picture){
        new DeleteAsyncTask(pictureDao).execute(picture);
    }

    public void deleteById(long id){
        new DeleteByIdAsyncTask(pictureDao).execute(id);
    }

    public LiveData<List<Picture>> getPicturesById(String id){

        return pictureDao.getPicturesById(id);
    }

    private static class InsertAsyncTask extends AsyncTask<Picture, Void, Void> {

        private PictureDao pictureDao;

        public InsertAsyncTask(PictureDao pictureDao){
            this.pictureDao = pictureDao;
        }
        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDao.insert(pictures[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Picture ,Void,Void>{

        private PictureDao pictureDao;

        public UpdateAsyncTask(PictureDao pictureDao){
            this.pictureDao = pictureDao;
        }
        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDao.update(pictures[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Picture ,Void,Void>{

        private PictureDao pictureDao;

        public DeleteAsyncTask(PictureDao pictureDao){
            this.pictureDao = pictureDao;
        }
        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDao.delete(pictures[0]);
            return  null;
        }
    }
    private static class DeleteByIdAsyncTask extends AsyncTask<Long ,Void,Void>{

        private PictureDao pictureDao;

        public DeleteByIdAsyncTask(PictureDao pictureDao){
            this.pictureDao = pictureDao;
        }
        @Override
        protected Void doInBackground(Long... longs) {
            pictureDao.deleteById(longs[0]);
            return  null;
        }
    }
}
