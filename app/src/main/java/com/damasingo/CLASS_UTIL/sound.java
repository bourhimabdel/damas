package com.damasingo.CLASS_UTIL;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;

public class sound {

    MediaPlayer son_collect;
    MediaPlayer song_click;
    MediaPlayer song_move;
    MediaPlayer song_eat;
    MediaPlayer song_timer;
    MediaPlayer song_lose;
    MediaPlayer song_win;
    MediaPlayer song_msg;


    public void collect(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
            son_collect = MediaPlayer.create(context, R.raw.coins_collect);
            son_collect.start();
            son_collect.setOnCompletionListener(MediaPlayer::release);
            }
    }

    public void msg(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
                song_msg = MediaPlayer.create(context, R.raw.msg);
                song_msg.start();
                song_msg.setOnCompletionListener(MediaPlayer::release);
            }
    }

    public void swip_to_move(Context context){
        if(context!=null )
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل") ) {
            song_move = MediaPlayer.create(context, R.raw.swip);
            song_move.start();
            song_move.setOnCompletionListener(MediaPlayer::release);
        }
    }

    public void swip_to_eat(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
            song_eat = MediaPlayer.create(context, R.raw.eat);
            song_eat.start();
            song_eat.setOnCompletionListener(MediaPlayer::release);
        }
    }

    public void click(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {

            song_click = MediaPlayer.create(context, R.raw.click);
            song_click.start();
            song_click.setOnCompletionListener(MediaPlayer::release);
        }
    }

    public void timer(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
            song_timer = MediaPlayer.create(context, R.raw.timer);
            song_timer.start();
            //song_timer.setOnCompletionListener(MediaPlayer::release);
        }

    }

    public void stop_timer(){
        if(song_timer!=null) {
            if(song_timer.isPlaying()) {
                song_timer.stop();
                song_timer.reset();
                song_timer.release();
                song_timer=null;
            }
        }

    }

    public void win(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
            song_win = MediaPlayer.create(context, R.raw.win);
            song_win.start();
            song_win.setOnCompletionListener(MediaPlayer::release);
        }
    }

    public void lose(Context context){
        if(context!=null)
            if(new sql_manager(context).get_all_setting().get(5).getChoice().equals("مفعل")) {
            song_lose = MediaPlayer.create(context, R.raw.lose);
            song_lose.start();
            song_lose.setOnCompletionListener(MediaPlayer::release);
        }
    }
}
