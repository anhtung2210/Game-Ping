package pl.itto.gameping.utils;

import pl.itto.gameping.R;

/**
 * Created by PL_itto on 11/21/2017.
 */

public class AppConstants {
    public static class GameSelect {
        public static final int DEFAULT_GAME_COUNT = 6;
        public static final int[] DEFAULT_GAME_THUMBS = {R.drawable.img_pubg, R.drawable.img_lol, R.drawable.img_dota, R.drawable.img_fornite, R.drawable.img_overwatch, R.drawable.img_csgo};

        public static class CustomServer {
            public static final String EXTRA_MODE = "extra_mode";
            public static final int MODE_CREATE = 0;
            public static final int MODE_EDIT = 1;

            public static final String EXTRA_TITLE = "extra_title";
            public static final String EXTRA_ADDRESS = "extra_address";


        }
    }

    public class AreaSelect {
        public static final String EXTRA_GAME_ITEM = "extra_game_item";
    }

    public class ServerPing {
        public static final String EXTRA_TITLE = "extra_title";
        public static final String EXTRA_ADDRESS = "extra_address";
    }

}
