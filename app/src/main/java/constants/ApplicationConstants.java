package constants;

/**
 * Created by prasadsawant on 11/4/16.
 */

public class ApplicationConstants {

    public static final String ACTION_TOKEN_RECEIVED = "com.solaps.action.token.received";
    public static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    public static final String SPLASH_BROADCAST_RESPONSE = "com.solaps.splash.broadcast.response";
    public static final String REGISTER_BROADCAST_RESPONSE = "com.solaps.register.broadcast.response";

    public static final String CHARGE_BOT_SHARED_PREFERENCES = "com.solaps.charge.bot.shared.preferences";
    public static final String PREFERENCE_IS_REGISTERED = "com.solaps.preference.is.registered";
    public static final String PREFERENCE_EMAIL = "com.solaps.preference.email";
    public static final String PREFERENCE_PASSWORD = "com.solaps.preference.password";
    public static final String PREFERENCE_FULL_NAME = "com.solaps.preference.full.name";
    public static final String PREFERENCE_IS_VERIFIED = "com.solaps.preference.is.verified";
    public static final String PREFERENCE_PLAYER_ID = "com.solaps.preference.player.id";
    public static final String PREFERENCE_FACEBOOK_FLAG = "com.solaps.preference.facebook.flag";
    public static final String PREFERENCE_FACEBOOK_ID = "com.solaps.preference.facebook.id";
    public static final String PREFERENCE_FACEBOOK_OAUTH = "com.solaps.preference.facebook.oauth";
    public static final String PREFERENCE_LATITUDE = "com.solaps.preference.latitude";
    public static final String PREFERENCE_LONGITUDE = "com.solaps.preference.longitude";
    public static final String PREFERENCE_AVATAR_PATH = "com.solaps.preference.avatar.path";
    public static final String PREFERENCE_AVATAR_FILE_NAME = "com.solaps.preference.avatar.file.name";
    public static final String PREFERENCE_AVATAR_URL = "com.solaps.preference.avatar.url";
    public static final String PREFERENCE_SUNRISE = "com.solaps.preference.sunrise";
    public static final String PREFERENCE_SUNSET = "com.solaps.preference.sunset";
    public static final String PREFERENCE_CONTACT_NO = "com.solaps.preference.contact.no";


    public static final String EXTRA_STATE_INITIALIZATION = "com.solaps.extra.state.initialization";
    public static final String EXTRA_STATE_REGISTER = "com.solaps.extra.state.register";
    public static final String EXTRA_EMAIL = "com.solaps.extra.email";
    public static final String EXTRA_PASSWORD = "com.solaps.extra.password";
    public static final String EXTRA_FIRST_NAME = "com.solaps.extra.first.name";
    public static final String EXTRA_LAST_NAME = "com.solaps.extra.last.name";
    public static final String EXTRA_DEVICE_ID = "com.solaps.extra.device.id";
    public static final String EXTRA_REWARD_NAME = "com.solaps.extra.reward.name";
    public static final String EXTRA_REWARD_DESCRIPTION = "com.solaps.extra.reward.description";
    public static final String EXTRA_REWARD_IMAGE_URL = "com.solaps.extra.reward.image.url";
    public static final String EXTRA_NOTIFICATION_DATA = "com.solaps.extra.notification.data";
    public static final String EXTRA_MODEL = "com.solaps.extra.model";
    public static final String EXTRA_TOKEN = "com.solaps.extra.token";

    public static final String STATE_REGISTER = "com.solaps.state.register";
    public static final String STATE_HOME = "com.solaps.state.home";
    public static final String STATE_REGISTER_SUCCESS = "com.solaps.state.success";
    public static final String STATE_REGISTER_FAILURE = "com.solaps.state.failure";
    public static final String STATE_LOGIN = "com.solaps.state.login";
    public static final String STATE_WELCOME = "com.solaps.state.welcome";

    public static final String HASH_FIRSTNAME = "com.solaps.hash.firstname";
    public static final String HASH_LASTNAME = "com.solaps.hash.lastname";
    public static final String HASH_EMAIL = "com.solaps.hash.email";
    public static final String HASH_DEVICE = "com.solaps.hash.device";
    public static final String HASH_DEVICE_ID = "com.solaps.hash.device.id";

    public static final String DAY_FORMAT = "EEEE";
    public static final String SEMICOLON_SEC = ":";

    public static final String FRAGMENT_HOME = "solaps.com.fragments.HomeFragment";
    public static final String FRAGMENT_PROFILE = "solaps.com.fragments.ProfileFragment";
    public static final String FRAGMENT_DEVICE = "solaps.com.fragments.DeviceFragment";


    public static final int MIN_TIME = 500;
    public static final int MIN_DISTANCE = 5000;


    public static final String HASH_NAME = "name";
    public static final String HASH_DESCRIPTION = "description";
    public static final String HASH_IMAGE_URL = "image_url";

    public static final String NOTIFICATION_BROADCAST_RESPONSE = "com.solaps.notification.broadcast.response";

    public static final String FACEBOOK_PARAMS_FIELDS = "fields";
    public static final String FACEBOOK_PARAMS_PICTURE = "picture";
    public static final String FACEBOOK_PARAMS_ME = "me";

    public static final String PDUS = "pdus";

    public static final String HOME_FRAGMENT_DATABASE = "homeFragment";
    public static final String HOME_FRAGMENT_TABLE_GENERAL = "general";
    public static final String HOME_FRAGMENT_COLUMN_ID = "_id";
    public static final String HOME_FRAGMENT_COLUMN_VALUE = "value";
    public static final String CREATE_GENERAL_TABLE = "CREATE TABLE " + HOME_FRAGMENT_TABLE_GENERAL + " (" + HOME_FRAGMENT_COLUMN_ID + " INTEGER PRIMARY KEY, " + HOME_FRAGMENT_COLUMN_VALUE + " TEXT )";
    public static final String DROP_GENERAL_TABLE = "DROP TABLE IF EXISTS " + HOME_FRAGMENT_TABLE_GENERAL;

    public static final int HOME_FRAGMENT_DATABASE_VERSION = 1;



    public static final int REQUIRED_SIZE = 70;

    public static final int FACEBOOK_REQUIRED_CODE = 64206;

    public static final int MY_PERMISSIONS_CODE = 100;
    public static final int SELECT_IMAGE = 1;
}
