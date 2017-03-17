package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.AbstractInstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.InstanceFactory;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.ProductService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.product.business.domain.ProductItem;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductActivityCache;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.products.ProductsActivity;
import rx.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 31.08.16 creation date
 */
public class SearchTextWatcher implements TextWatcher
{
    private ProductService productService;
    private ProductActivityCache cache;
    private Subscription searchSubscription;

    public SearchTextWatcher(ProductActivityCache cache)
    {
        this.cache = cache;
        AbstractInstanceFactory instanceFactory = new InstanceFactory(cache.getActivity());
        this.productService = (ProductService) instanceFactory.createInstance(ProductService.class);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        String[] searchedTexts = s.toString().split(" ");
        List<ProductItem> resultItems = new ArrayList<>();

        if ( searchSubscription != null )
        {
            searchSubscription.unsubscribe();
        }

        searchSubscription = productService.getAllProducts(cache.getListId())
                .filter(item -> productService.isSearched(searchedTexts, item))
                .doOnNext(item -> resultItems.add(item))
                .doOnCompleted(() ->
                {
                    ProductsActivity activity = (ProductsActivity) cache.getActivity();
                    activity.setProductsAndUpdateView(resultItems);
                    activity.reorderProductViewBySelection();
                    setErrorMessage(resultItems);
                })
                .subscribe();
    }

    private void setErrorMessage(List<ProductItem> resultItems)
    {
        if ( resultItems.isEmpty() )
        {
            cache.getSearchTextInputLayout().setError(cache.getActivity().getResources().getString(R.string.no_products_found));
        }
        else
        {
            cache.getSearchTextInputLayout().setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }
}
