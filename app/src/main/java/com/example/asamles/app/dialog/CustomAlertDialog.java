public class CustomAlertDialog extends DialogFragment {
    /** The system calls this to get the DialogFragment's layout, regardless
        of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
		View rootView = inflater.inflate(R.layout.purchase_items, container, false);
		
		ImageView background = (ImageView) rootView.findViewById(R.id.image);
		
		container.setDrawingCacheEnabled(true);
        container.buildDrawingCache(true);
        Bitmap cs = Bitmap.createBitmap(container.getDrawingCache());
		background.setImageBitmap(cs);
        container.setDrawingCacheEnabled(false);
		
        return rootView;
    }
  
    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}