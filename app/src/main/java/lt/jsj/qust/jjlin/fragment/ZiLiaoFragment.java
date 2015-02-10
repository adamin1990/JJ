package lt.jsj.qust.jjlin.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.Constant;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.beans.AlbumBean;
import lt.jsj.qust.jjlin.beans.JJYinYueTaiMvModel;
import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.dao.AlbumDao;
import lt.jsj.qust.jjlin.dao.MusicDao;
import lt.jsj.qust.jjlin.dao.YinYueTaiDao;
import lt.jsj.qust.jjlin.views.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZiLiaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZiLiaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZiLiaoFragment extends Fragment {
    private String profileurl="http://y.qq.com/#type=singer&mid=001BLpXF2DyJe2"; //资料
    private static final String ARG_POSITION = "position";
    private List<JJYinYueTaiMvModel> mvModelLists= new ArrayList<JJYinYueTaiMvModel>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position;
    private TextView text;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private ScrollView SC;
  private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<AlbumBean> beans=new ArrayList<AlbumBean>();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZiLiaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZiLiaoFragment newInstance(String param1, String param2) {
        ZiLiaoFragment fragment = new ZiLiaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ZiLiaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            position=getArguments().getInt(ARG_POSITION);
        }
    }
      Music  music;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        new Thread( new Runnable() {
//            @Override
//            public void run() {
////                mvModelLists=YinYueTaiDao.getJJYinYueTaiMvs(profileurl);
////                music= MusicDao.getMusicByName("就是我",1,1);
//
//                    beans= AlbumDao.getAlbum();
//                    System.out.print("");
//
//
//            }
//        }).start();
        View view=inflater.inflate(R.layout.fragment_zi_liao,container,false);

            text= FindView.findViewById(view,R.id.profiletextview);
        text2=FindView.findViewById(view,R.id.profiletextview2);
        text3=FindView.findViewById(view,R.id.profiletextview3);
        text4=FindView.findViewById(view,R.id.profiletextview4);
        text5=FindView.findViewById(view,R.id.profiletextview5);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());
        text.setText(Html.fromHtml(Constant.prifile));
        text2.setText(Html.fromHtml(Constant.jianjie));
        text3.setText(Html.fromHtml(Constant.ziliao));
        text4.setText(Html.fromHtml(Constant.congye));
        text5.setText(Html.fromHtml(Constant.rongyu));
        SC=FindView.findViewById(view,R.id.scrollview);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabButton);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             SC.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        switch (position) {
            case 0:
                fab.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                break;
            case 1:
                fab.setBackgroundColor(getResources().getColor(R.color.red));

                break;
            case 2:
                fab.setBackgroundColor(getResources().getColor(R.color.blue));

                break;
            case 3:
                fab.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));

                break;
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public static ZiLiaoFragment newInstance(int position) {
        ZiLiaoFragment f = new ZiLiaoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
}
