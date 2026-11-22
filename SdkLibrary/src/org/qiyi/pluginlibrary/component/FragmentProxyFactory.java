/*
 *
 * Copyright 2018 iQIYI.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.qiyi.pluginlibrary.component;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.qiyi.pluginlibrary.R;
import org.qiyi.pluginlibrary.constant.IntentConstant;
import org.qiyi.pluginlibrary.utils.ErrorUtil;

/**
 * 宸ュ巶绫伙紝璐熻矗鍒涘缓 FragmentProxy锛屾牴鎹?Neptune 閰嶇疆锛屽喅瀹氬叿浣撶殑 FragmentProxy 绫诲瀷
 */
public class FragmentProxyFactory {

    /**
     * 鏍规嵁 packageName 涓?classname 鍒涘缓 FragmentProxy
     *
     * @param proxyClass  FragmentProxy 鍏蜂綋绫诲瀷
     * @param packageName 鎻掍欢鍖呭悕
     * @param className   鎻掍欢 Fragment 绫诲悕
     * @return FragmentProxy
     */
    @NonNull
    public static AbstractFragmentProxy create(Class<? extends AbstractFragmentProxy> proxyClass, String packageName, String className) {
        AbstractFragmentProxy fragment;
        if (proxyClass == null) {
            fragment = new DefaultFragmentProxy();
        } else {
            try {
                fragment = proxyClass.newInstance();
            } catch (Throwable e) {
                ErrorUtil.throwErrorIfNeed(e);
                fragment = new DefaultFragmentProxy();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentConstant.EXTRA_TARGET_PACKAGE_KEY, packageName);
        bundle.putString(IntentConstant.EXTRA_TARGET_CLASS_KEY, className);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 榛樿鐨?FragmentProxy锛屼竴鑸帴鍏ユ柟鍦ㄦ湁瀹氬埗 UI 闇€姹傦紝闇€瑕佽嚜宸辩户鎵?AbstractFragmentProxy 瀹炵幇
     */
    public static class DefaultFragmentProxy extends AbstractFragmentProxy {
        private View loadingView;
        private View errorView;

        @Override
        protected View onCreateUi(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_proxy_default, container, false);
            loadingView = view.findViewById(R.id.loading_view);
            errorView = view.findViewById(R.id.error_view);
            return view;
        }

        @Override
        protected void onLoadPluginFragmentSuccess(FragmentManager fragmentManager, Fragment fragment, String packageName) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        }

        @Override
        protected void onLoadPluginFragmentFail(int errorType, String packageName) {
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }
    }
}

