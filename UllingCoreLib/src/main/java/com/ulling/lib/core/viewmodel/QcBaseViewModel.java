package com.ulling.lib.core.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * AndroidViewModel ㄴ ViewModel클래스는 UI 관련 데이터를 라이프 사이클을 고려한 방식으로 저장하고 관리하도록 설계되었습니다. ㄴViewModel클래스를
 * 사용하면 화면 회전과 같은 구성 변경시에도 데이터를 유지할 수 있습니다.
 * <p>
 * 구글 예제 ㄴ https://github.com/googlesamples/android-architecture-components
 * <p>
 * 참고 구글 문서 ㄴ https://developer.android.com/topic/libraries/architecture/viewmodel ㄴ
 * https://medium.com/google-developers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4
 * <p>
 * <p>
 * <p>
 * UI 데이터를 가지는 헬퍼 클래스
 * <p>
 * 공급자(뷰모델)은 소비자(뷰)가 누구인지 알 필요가 없다 불특정다수 그러므로, 뷰에 대한 정보가 없다 즉, 뷰에서 요청한 데이터전달과 화면 이동등 비지니스 로직만 구현
 * <p>
 * 이 ViewModel클래스는 UI 관련 데이터를 라이프 사이클을 고려한 방식으로 저장하고 관리하도록 설계되었습니다. 이 ViewModel클래스를 사용하면 화면 회전과 같은
 * 구성 변경시에도 데이터를 유지할 수 있습니다.
 * <p>
 * <p>
 * <p>
 * ViewModel은 앱의 View를 위한 model입니다. View가 추상화 된 것이죠.
 * <p>
 * ViewModel은 DataModel로부터 필요한 데이터를 받고, UI 로직을 적용한 뒤 View가 소비하는 데이터를 노출시킵니다. DataModel과 비슷하게,
 * ViewModel은 Observable을 통해서 데이터를 노출시킵니다.
 * <p>
 * ex) 우리는 유저의 모든 동작들이 ViewModel을 통하도록 만들었고, 모든 뷰의 로직들이 ViewModel에 있도록 하였습니다. View의 상태를 노출시켜야 합니다.
 * 예를들어, 만약 User 객체의 이름과 이메일을 나타내야 한다면, 두개의 스트림을 만드는 것 보다는, DisplayableUser 을 만들어 이 두가지를 하나로 감싸는 것이
 * 더 좋습니다. 이 스트림이 매번 이름과 이메일이 바뀔때마다 정보를 내보낼 것입니다
 */
public abstract class QcBaseViewModel extends AndroidViewModel {
//    public Context qCon;
//    public FragmentActivity qActivity;
//    public QcBaseLifeFragment qFrag;

    public QcBaseViewModel(@NonNull Application application) {
        super(application);
    }

//    public void needInitViewModel(FragmentActivity qActivity) {
//        QcLog.e("needInitViewModel == ");
//        this.qActivity = qActivity;
//        this.qCon = qActivity.getApplicationContext();
//    }
//
//    public void needInitViewModel(FragmentActivity qActivity, QcBaseLifeFragment qFrag) {
//        QcLog.e("needInitViewModel == ");
//        this.qActivity = qActivity;
//        this.qFrag = qFrag;
//        this.qCon = qFrag.qCon;
//    }

//    /**
//     * 뷰모델에서 사용할 모델을 정의하고
//     * 로컬 데이터가 없는 경우, 네트워크에서 데이터를 가져오게 정의
//     *
//     * @param dbTypeLocal
//     * @param remoteType
//     * @param baseUrl
//     */
//    public abstract void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl);

    @Override
    protected void onCleared() {
//        if (mDatabaseModel != null)
//            mDatabaseModel.onCleared();
        super.onCleared();
    }
}
