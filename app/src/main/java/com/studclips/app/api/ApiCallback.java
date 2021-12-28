package com.studclips.app.api;

import com.studclips.app.model.GetVideosResponse;
import com.studclips.app.model.LikeUnlikeResposne;
import com.studclips.app.model.SearchUserResposne;
import com.studclips.app.model.SignUpResponse;
import com.studclips.app.model.AddVideoSuccess;

import com.studclips.app.model.ForgotPassResposne;

public interface ApiCallback {
    interface PlayerSignUpCallBack extends BaseInterFace {
        void onSuccessPlayerSignUp(SignUpResponse response);
    }

    interface SignInCallBack extends BaseInterFace {
        void onSuccessSignIn(SignUpResponse response);
    }

    interface FanSignUpCallBack extends BaseInterFace {
        void onSuccessFanSignUp(SignUpResponse response);
    }

    interface LogoutCallBack extends BaseInterFace {
        void onSuccessLogout(String  response);
    }

    interface ForgotPasswordCallBack extends BaseInterFace {
        void onSuccessForgotPass(ForgotPassResposne response);
    }

    interface UpdateProfilePlayer extends BaseInterFace {
        void onSuccessProfile(SignUpResponse response);
    }

    interface UpdateSettings extends BaseInterFace {
        void onSuccessUpdateSetting(SignUpResponse response);
    }

    interface Subscription extends BaseInterFace {
        void onSuccessSubscription(String response);
    }
    interface AddVideo extends BaseInterFace {
        void onSuccessAddVideo(AddVideoSuccess response);
    }

    interface GetVideos extends BaseInterFace {
        void onSuccessGetVideo(GetVideosResponse response);
    }


    interface LikeUnlikeVideos extends BaseInterFace {
        void onSuccessLikeUnlikeVideo(LikeUnlikeResposne response);
    }

    interface RatingVideos extends BaseInterFace {
        void onRatingVideo(LikeUnlikeResposne response);
    }

    interface ViewCall extends BaseInterFace {
        void onSuccessView(LikeUnlikeResposne response);
    }

    interface SearchUserCall extends BaseInterFace {
        void onSuccessSearchUser(SearchUserResposne response);
    }
}
