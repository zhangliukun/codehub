package zalezone.retrofitlibrary.presentation.presenter;

import android.content.Context;
import android.text.TextUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import okhttp3.Headers;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.network.IDataCallback;
import zalezone.retrofitlibrary.presentation.contract.MainActivityContract;


/**
 * Created by zale on 2017/3/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({GithubApi.class, AccountManager.class})
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})

public class MainActivityPresenterTest {

//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    @Captor
    ArgumentCaptor<IDataCallback> mCallbackArgumentCaptor;

    @Mock
    MainActivityContract.View view;

    @Mock
    Context context;

    @Before
   public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
   }

    @Test
    public void start() throws Exception {
        List mockList = Mockito.mock(List.class);

        mockList.add("a");
        mockList.add("b");

        InOrder inOrder = Mockito.inOrder(mockList);
        inOrder.verify(mockList).add("a");
        inOrder.verify(mockList).add("b");

        Mockito.when(mockList.get(0)).thenReturn("zale is amazing");
        Assert.assertEquals("zale is amazing",mockList.get(0));

        Mockito.verify(mockList,Mockito.times(1)).get(0);

        Mockito.when(mockList.get(Mockito.anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args= invocation.getArguments();
                Object mock = invocation.getMock();
                return args;
            }
        });

    }

    @Test
    public void githubLogin() throws Exception {
        PowerMockito.mockStatic(GithubApi.class);
        PowerMockito.mockStatic(AccountManager.class);

        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(view);
        mainActivityPresenter.githubLogin("zale","zkljsdf");
        //Mockito.verify(view).showOnLoading();

//        Mockito.when((GithubApi.login(Mockito.anyString(),Mockito.anyString(),Mockito.any(IDataCallback.class)))).thenAnswer(new Answer<Object>() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                Object[] arguments = invocation.getArguments();
//                IDataCallback iDataCallback = (IDataCallback) arguments[2];
//                iDataCallback.onSuccess(Mockito.any(),Mockito.any(Headers.class));
//                return null;
//            }
//        });

        PowerMockito.verifyStatic(Mockito.times(1));
        GithubApi.login(Mockito.anyString(),Mockito.anyString(),mCallbackArgumentCaptor.capture());

//        mCallbackArgumentCaptor = ArgumentCaptor.forClass(IDataCallback.class);
//        PowerMockito.when(GithubApi.class,"login",Mockito.anyString(),Mockito.anyString(),mCallbackArgumentCaptor.capture());
        Authorization authorization = new Authorization();
        Headers headers = null;
        mCallbackArgumentCaptor.getValue().onSuccess(authorization,headers);

        PowerMockito.verifyStatic(Mockito.times(1));
//        //PowerMockito.when(AccountManager.class,"saveLoginToken",context,Mockito.anyString());
        AccountManager.saveLoginToken(Mockito.any(Context.class),Mockito.anyString());

        boolean result = authorization!=null && !TextUtils.isEmpty(authorization.getToken());
        System.out.print(authorization.getToken());
        Assert.assertTrue(result);
    }

    @Test
    public void githubUserInfo() throws Exception {

    }

    @Test
    public void checkLogin() throws Exception {

    }

    @Test
    public void loginOrOut() throws Exception {

    }

    @Test
    public void showLogoutConfirmDialog() throws Exception {

    }

}