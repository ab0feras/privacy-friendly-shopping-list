package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.deleteproducts.DeleteProductsActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main.MainActivity;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 21.07.16 creation date
 */
public class ShowDeleteProductsOnClickListener implements View.OnClickListener
{
    private ProductActivityCache cache;

    public ShowDeleteProductsOnClickListener(ProductActivityCache cache)
    {
        this.cache = cache;
    }

    @Override
    public void onClick(View v)
    {
        AppCompatActivity activity = cache.getActivity();
        Intent intent = new Intent(activity, DeleteProductsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.LIST_ID_KEY, cache.getListId());
        activity.startActivity(intent);
    }
}