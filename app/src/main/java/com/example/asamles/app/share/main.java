//public class MainActivity extends Activity implements OnNavigationListener {
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_main);
//    ActionBar bar = getActionBar();
//    ArrayList<String> list = new ArrayList<String>();
//    list.add("bar 0 ");
//    list.add("bar 2 ");
//    list.add("bar 3 ");
//    list.add("bar 4 ");
//    Adapter adapter = new Adapter();
//
//    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//    bar.setListNavigationCallbacks(adapter.getAdapter(this, list, "Controls"), this);
//    adapter.setSeekBarListener( new SeekBarListener(){
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList) {
//            Log.i("", "onProgressChanged " + progress + " position in list" + positionInList);
//
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar, int positionInList) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar, int positionInList) {
//            // TODO Auto-generated method stub
//
//        }
//
//    });
//}
//
//
//@Override
//public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//    // TODO Auto-generated method stub
//    return false;
//}