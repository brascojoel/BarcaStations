package com.example.barcastations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewModel extends AndroidViewModel{

    private PictureRepository pictureRepository;
    private LiveData<List<Picture>> listPictures;

    public ListViewModel(Application application){
        super(application);
        pictureRepository = new PictureRepository(application);

    }

    public void insert(Picture picture){
         pictureRepository.insert(picture);
    }

    public void update(Picture picture){
         pictureRepository.update(picture);
    }

    public void delete(Picture picture){
         pictureRepository.delete(picture);
    }

    public void deleteById(long id){
         pictureRepository.deleteById(id);
    }
    public LiveData<List<Picture>> getPicturesById(String id){
        return pictureRepository.getPicturesById(id);
    }
    // TODO: Implement the ViewModel
}
