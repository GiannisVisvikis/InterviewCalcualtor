package visvikis.ioannis.interviewcalculator;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;


/*
     Load the sound in the background from the assets folder
 */

public class SoundLoader extends AsyncTaskLoader<MediaPlayer>
{

    private MediaPlayer result;
    private Context context;


    public SoundLoader(@NonNull Context context)
    {
        super(context);

        this.context = context;

    }


    @Override
    protected void onStartLoading()
    {
        if(result != null)
            deliverResult(result);
        else
            forceLoad();
    }


    @Nullable
    @Override
    public MediaPlayer loadInBackground()
    {
        MediaPlayer result = null;

        try {

            AssetFileDescriptor afd = this.context.getAssets().openFd("sounds/coins_in_cloth.wav");
            //Log.e("AFD_IS_NULL", (afd == null) + "");
            FileDescriptor fd = afd.getFileDescriptor();

            result= new MediaPlayer();
            result.setDataSource(fd, afd.getStartOffset(), afd.getLength());
            result.prepare();

            result.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    mediaPlayer.release();
                }
            });

        }
        catch (IOException io){
            Toast.makeText(this.context, "Sound not found", Toast.LENGTH_SHORT).show();
        }

        return result;
    }


    @Override
    public void deliverResult(@Nullable MediaPlayer data)
    {
        if(this.result == null)
            this.result = data;

        super.deliverResult(data);
    }



}
