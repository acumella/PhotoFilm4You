package ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import jpa.BrandJPA;
import jpa.ItemJPA;
import jpa.ModelJPA;
import jpa.ProductJPA;
import jpa.ItemJPA.ItemStatus;

/**
 * Session EJB Remote Interfaces
 * @param <itemStatus>
 */
@Remote
public interface CatalogFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	  public String deleteCategory(int categoryId);
	  public String addCategory(String name, Integer parentCategoryId);
	  public Collection<?> listAllCategories();
	  public String updateCategory(Integer categoryId, String name, Integer parentCategoryId);
	  public Collection<?> listCatalogElements(String name);
	  public void deleteProduct(Integer productId);
	  public String addProduct(String name, String description, Integer categoryId, Integer ModelId, Integer brandId,  Integer dailyPrice);
	  public void updateProduct(Integer productId, String name, String description, Integer categoryId, Integer ModelId, Integer brandId,  Integer dailyPrice, Integer availableItems);
	  public Collection<?> listAllProducts();
	  public Collection<?> listCategoryProducts(Integer id);
	  public ProductJPA getProductInformation(Integer productId);
	  public Collection<?> listAllItems();
	  public String addItem(String serialNumber, String itemStatus, Integer productId);
	  public void deleteItem(String serialNumber);
	  public String updateProductItem(String serialNumber, String status, Integer productId);
	  public Collection<?> listProductsItems(Integer product);
	  public ItemJPA getProductItem(String serialNumber);
	  public String addBrand(String name);
	  public void updateBrand(Integer brandId, String name);
	  public void deleteBrand(Integer brandId, String name);
	  public Collection<?> listAllBrands();
	  public String addModel(String name, int id);
	  public void updateModel(Integer modelId, String name);
	  public void deleteModel(Integer brandId);
	  public Collection<?> listAllModels();
	  public Collection<ItemJPA> listAvailableItems(Date dataInit, Date dataFinal);
	  public Collection<?> listProductAvailableItems(Date dataInit, Date dataFinal);
	  public Collection<?> listProductAvailableItems(Integer productId, Date dataInit, Date dataFinal);
	  
	  public BrandJPA getBrandById(Integer id);
	  public ModelJPA getModelById(Integer id);
}
