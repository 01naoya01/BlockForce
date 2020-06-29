package com.example.blockforce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class HowToPlayDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("How TO PLAY")
            .setMessage("・操作方法\n右，左，下にスライドすればその方向にブロックが移動します．ブロックを回転させたい場合は右回転矢印のボタン，あるいは左回転矢印のボタンを押してください．ブロックを素早く下に落としたい場合は下矢印のボタンを押してください．\n\n・ゲーム説明\n10行消すごとにLevelが1上がり，ブロックが下に落ちる速度が上昇します．新しくブロックが生成される位置にブロックが設置されている場合はゲームオーバーとなり，消した行の総数(Lines)が表示されます．")
            .setNeutralButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }
            )
            .create();
    }
}
