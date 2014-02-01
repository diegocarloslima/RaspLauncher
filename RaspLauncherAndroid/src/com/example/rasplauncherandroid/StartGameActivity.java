package com.example.rasplauncherandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StartGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        
//        final Twitter twitter = TwitterFactory.getSingleton();
//        twitter.setOAuthConsumer("", "");
        
        AndroidClientSocket.getInstance().open();
        
        final EditText startGameUsername = (EditText) findViewById(R.id.start_game_username);
        
        final View startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(StartGameActivity.this, PlayGameActivity.class);
				startActivity(intent);
			}
		});
    }
    
    @Override
    protected void onDestroy() {
    	AndroidClientSocket.getInstance().close();
    	
    	super.onDestroy();
    }
}
