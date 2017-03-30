package com.example.sphinx.mix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sphinx.mix.entity.LoginRequest;
import com.example.sphinx.mix.entity.LoginResponse;
import com.example.sphinx.mix.entity.RegisterRequest;
import com.example.sphinx.mix.entity.RegisterResponse;
import com.example.sphinx.mix.downmvp.ientity.API;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "DemoRx";
    private final Context mContext = MainActivity.this;
    private Button btnStart;
    private Button btnRequest;
    private Button btnJump;
    private Button btnCoo;

    //控制关闭流
    private Disposable mDisposable;
    //当有多个事件时,管理关闭
    private CompositeDisposable mCompositeDisposable;
    private Subscription mSubscription;

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* mDisposable.dispose();
        mCompositeDisposable.clear();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
//        initDemoRx();   //初步使用
//        initDemoRX02(); //不同线程
//        doRequest();    //和Retrofit结合,发起请求 CompositeDisposable
//        register();     //两个事件的承接处理  flagMap和concatMap

//        zipTwo();   //线程对两个事件zip的影响,存在异常
//        processOOM();  //水缸溢满的解决办法 filter/sample/sleep
//        processOOMWithFlowable();   //水缸溢出的解决办法  flowable 缓存大小128

//        flowableSize();   //flowable灵活处理  drop/latest/buffer
//        definedFlowable();  //自定义的Flowable   onBackpressureDrop等对应策略
//        sendFlowableEmitter();   // 上游判断是否继续发送消息
    }

    private void sendFlowableEmitter() {
        //同步
        synchroniz();
        //异步,下游每消费96个事件便会自动触发内部的request()去设置上游的requested的值
        unsynchroniz();
    }

    private void unsynchroniz() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "current requested: " + emitter.requested());
//                        此时打印结果是128
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(222);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void synchroniz() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "before emit, requested = " + emitter.requested());

                        Log.d(TAG, "emit 1");
                        emitter.onNext(1);
                        Log.d(TAG, "after emit 1, requested = " + emitter.requested());

                        Log.d(TAG, "emit 2");
                        emitter.onNext(2);
                        Log.d(TAG, "after emit 2, requested = " + emitter.requested());

                        Log.d(TAG, "emit 3");
                        emitter.onNext(3);
                        Log.d(TAG, "after emit 3, requested = " + emitter.requested());

                        Log.d(TAG, "emit complete");
                        emitter.onComplete();

                        Log.d(TAG, "after emit complete, requested = " + emitter.requested());
                    }
                }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(100);
                        s.request(1000);// 可以多次调用,最终是1100次
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void definedFlowable() {
        Flowable.interval(1, TimeUnit.MICROSECONDS)
                .onBackpressureDrop()  //加上背压策略
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 水缸的更多处理
     */
    private void flowableSize() {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
                //DROP和LATEST的区别在于,当发出事件数量有限时,后者一定会接收到最后一条数据,如这里的9999,而DROP是连续性的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(128);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 利用Flowable解决OOM
     */
    private void processOOMWithFlowable() {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR); //当出现速度不一致时,会抛出MissingBackpressureException,但程序不会崩溃

        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(3);  //池的处理能力,必要代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream.subscribe(downstream);
    }

    /**
     * 控制速度或者事件数量解决OOM
     */
    private void processOOM() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; ; i++) {
                    e.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 100 == 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, integer + "");
                    }
                });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .sample(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, integer + "");
                    }
                });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                    Thread.sleep(2000);  //每次发送完事件延时2秒
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });
    }

    /**
     * 将两个管道进行结合,线程相同/不同,是否睡眠
     */
    private void zipTwo() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Thread.sleep(1000);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Thread.sleep(1000);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Thread.sleep(1000);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Thread.sleep(1000);

                Log.d(TAG, "emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d(TAG, "emit C");
                emitter.onNext("C");
                Thread.sleep(1000);

                Log.d(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    /**
     * 注册成功之后进行登录,主要利用线程切换和flagMap
     */
    private void register() {
        final API api = createRetrofit().create(API.class);
        api.register(new RegisterRequest())
                .subscribeOn(Schedulers.io())  //发起注册网络请求
                .observeOn(AndroidSchedulers.mainThread()) //在主线程处理注册结果
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        //根据注册结果做出相应的反应,如跳转界面
                    }
                })
                .observeOn(Schedulers.io()) //发起登录网络请求,另有concatMap,顺序执行
                .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                    @Override
                    public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                        return api.login(new LoginRequest());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //主线程处理登录结果
                .subscribe(new Consumer<LoginResponse>() {
                               @Override
                               public void accept(LoginResponse loginResponse) throws Exception {
                                   //onNext成功
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //onError失败
                            }
                        }
                );

    }

    private void doRequest() {
        API api = createRetrofit().create(API.class);
        LoginRequest request = new LoginRequest();
        api.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        mCompositeDisposable = new CompositeDisposable();
                        mCompositeDisposable.add(mDisposable);
                    }

                    @Override
                    public void onNext(LoginResponse value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //    创建Retrofit
    private Retrofit createRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //addNetworkInterceptor有何区别
            builder.addInterceptor(interceptor);
        }
        return new Retrofit.Builder().baseUrl("www.example.com")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void initDemoRX02() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.e(TAG, "emit 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.e(TAG, "onNext: " + integer);
            }
        };

//        observable.subscribe(consumer);

        /*
        * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效.
          多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
        * */
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    private void initDemoRx() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
/**
 * ObservableEmitter： Emitter是发射器的意思，这个就是用来发出事件的，它可以发出三种类型的事件，
 * 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
 */
                e.onNext(0);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
//                error和complete不能同时出现,同时出现会抛出error中的异常
//                e.onError(new Exception("出错啦,>.<"));
            }
        }).subscribe(new Observer<Integer>() {
            /*
            * 不带任何参数的subscribe() 表示下游不关心任何事件
              带有一个Consumer参数的方法表示下游只关心onNext事件
            * */

            /**
             Disposable, 一次性用品.当调用它的dispose()方法时, 它就会将两根管道切断, 从而导致下游收不到事件.
             注意: 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
             */
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "subscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "" + value);
                if (value == 2) {
                    mDisposable.dispose();
                    Log.e(TAG, "isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "complete");
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnRequest = (Button) findViewById(R.id.btn_request);
        btnJump = (Button) findViewById(R.id.btn_jump);
        btnCoo = (Button) findViewById(R.id.btn_coo);

        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initListener() {
        btnStart.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
        btnJump.setOnClickListener(this);
        btnCoo.setOnClickListener(this);

        findViewById(R.id.btn_flex).setOnClickListener(this);
        findViewById(R.id.btn_dagger).setOnClickListener(this);
        findViewById(R.id.btn_dagger2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
//                flowableSize();
                break;
            case R.id.btn_request:
//                requestOnce();
                break;
            case R.id.btn_jump:
                Intent intent = new Intent(MainActivity.this, DownLoadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_coo:
                Intent intentCoo = new Intent(MainActivity.this, MainCoordinatorActivity.class);
                startActivity(intentCoo);
                break;
            case R.id.btn_flex:
                //打开另一应用.第二句避免打开的不是同一应用?
                Intent intent_flex = mContext.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.flexbox");
//                intent_flex.setComponent(ComponentName.unflattenFromString("com.google.android.apps.flexbox/.MainActivity"));
                mContext.startActivity(intent_flex);
                break;
            case R.id.btn_dagger:
                Intent intent_d = new Intent(MainActivity.this, DaggerMainActivity.class);
                startActivity(intent_d);
                break;
            case R.id.btn_dagger2:
                Intent intent_d2 = new Intent(MainActivity.this, Dagger2Activity.class);
                startActivity(intent_d2);

            default:
                break;
        }
    }

    private void requestOnce() {
        mSubscription.request(128);
    }
}
