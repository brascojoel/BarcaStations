package com.example.barcastations;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ShareFactory extends ViewModelProvider.NewInstanceFactory {


    private SharedViewModel myViewModel;

    private final Map<Class<? extends ViewModel>, ViewModel> mFactory = new HashMap<>();

    public ShareFactory(SharedViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        mFactory.put(modelClass, myViewModel);

        if (SharedViewModel.class.isAssignableFrom(modelClass)) {
            SharedViewModel shareVM = null;

            if (mFactory.containsKey(modelClass)) {
                shareVM = (SharedViewModel) mFactory.get(modelClass);
            } else {
                try {
                    shareVM = (SharedViewModel) modelClass.getConstructor(Runnable.class).newInstance(new Runnable() {
                        @Override
                        public void run() {
                            mFactory.remove(modelClass);
                        }
                    });
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (InstantiationException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                }
                mFactory.put(modelClass, shareVM);
            }

            return (T) shareVM;
        }
        return super.create(modelClass);
    }
}
