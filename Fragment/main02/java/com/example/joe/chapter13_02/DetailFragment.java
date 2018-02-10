package com.example.joe.chapter13_02;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//繼承自Support Library的Fragment類別
public class DetailFragment extends Fragment {

    // 顯示標題、內容和圖片用的畫面元件
    private TextView title_text, content_text;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 載入指定的畫面配置資源
        View result = inflater.inflate(R.layout.fragment_detail,
                                       container, false);
        // 讀取與設定畫面元件
        content_text = result.findViewById(R.id.content_text);
        title_text = result.findViewById(R.id.title_text);
        image = result.findViewById(R.id.image);
        return result;
    }

    // 更新畫面元件內容
    public void updateDetail(int position) {
        title_text.setText(DataSet.rpis[position]);
        content_text.setText(DataSet.contents[position]);
        image.setImageResource(DataSet.images[position]);
    }

}