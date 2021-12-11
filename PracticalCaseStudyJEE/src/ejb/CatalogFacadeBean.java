package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.BrandJPA;
import jpa.CategoryJPA;
import jpa.ItemJPA;
import jpa.ItemJPA.ItemStatus;
import jpa.ModelJPA;
import jpa.ProductJPA;
import jpa.ProductRattingJPA;
import jpa.RentItemJPA;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */

@Stateless
public class CatalogFacadeBean implements CatalogFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	@Override
	public String deleteCategory(int categoryId) {
		try {
			String query = "delete from practicalcase.category where id  = '" + categoryId + "';";
			entman.createNativeQuery(query).executeUpdate();
			return "Category successfully deleted";			
		}
		catch (PersistenceException e) {
			System.out.println(e);
			return "Exception called, Category NOT deleted" + e.toString();
		}
		
	}

	@Override
	public String addCategory(String name, Integer parentCategoryId) {
		CategoryJPA category = new CategoryJPA();
		CategoryJPA parent = entman.find(CategoryJPA.class, parentCategoryId);
		category.setName(name);
		if(parent != null)
			category.setParentCategory(parent);
		entman.persist(category);
		return "Category successfully added";		
	}
	
	/**
	 * Method that returns Collection of all Categories
	 * Not originally in specification
	 */
	@Override
	public Collection<CategoryJPA> listAllCategories() {
		@SuppressWarnings("unchecked")
		Collection<CategoryJPA> allCategories = entman.createQuery("from CategoryJPA").getResultList();
		return allCategories;
	}

	@Override
	public String updateCategory(Integer categoryId, String name, Integer parentCategoryId) {
		try {
			CategoryJPA category = entman.find(CategoryJPA.class, categoryId);
			category.setName(name);
			if (parentCategoryId != 0) { 
				category.setParentCategory(entman.find(CategoryJPA.class, parentCategoryId));
				entman.persist(category);
			}
			return "Category successfully updated";
		}
		catch (PersistenceException e){
			System.out.println(e);
			return "Exception called, Category NOT updated" + e.toString();
		}

	}

	@Override
	public Collection<?> listCatalogElements(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Integer productId) {
		try {
			String query = "delete from practicalcase.product where id  = '" + productId + "';";
			entman.createNativeQuery(query).executeUpdate();			
		}
		catch (PersistenceException e) {
			System.out.println(e);
		}
		
	}

	/**
	 * Method that returns Collection of all Categories
	 * Not originally in specification
	 */
	@Override
	public Collection<ProductJPA> listAllProducts() {
		@SuppressWarnings("unchecked")
		Collection<ProductJPA> allProducts = entman.createQuery("from ProductJPA").getResultList();
		//update ratings every time listing is invoked
		for (Iterator<ProductJPA> iter2 = allProducts.iterator(); iter2.hasNext();)
		{
			ProductJPA product2 = (ProductJPA) iter2.next();
			product2.setProductRatting(calculateProductRating(product2.getId()));
		}
		return allProducts;
	}
	
	@Override
	public String addProduct(String name, String description, Integer categoryId, Integer modelId, Integer brandId,  Integer dailyPrice) {
		ProductJPA product = new ProductJPA();
		product.setCategory(entman.find(CategoryJPA.class, categoryId));
		product.setName(name);
		product.setbrand(entman.find(BrandJPA.class, brandId));
		product.setmodel(entman.find(ModelJPA.class, modelId));
		product.setDailyPrice(dailyPrice);
		product.setDescription(description);
		//new product, no available items
		product.setAvailableItems(0);
		//new product, set rating to 0		
		product.setProductRatting(0.0f);
		entman.persist(product);
		return "Product successfully added";
		
	}

	@Override
	public void updateProduct(Integer productId, String name, String description, Integer categoryId, Integer modelId, Integer brandId,  Integer dailyPrice, Integer availableItems) {
		try {
			ProductJPA product = entman.find(ProductJPA.class, productId);
			product.setName(name);
			product.setDescription(description);
			product.setCategory(entman.find(CategoryJPA.class, categoryId));
			product.setmodel(entman.find(ModelJPA.class, modelId));
			product.setbrand(entman.find(BrandJPA.class, brandId));
			product.setDailyPrice(dailyPrice);
			product.setAvailableItems(availableItems);
		}
		catch (PersistenceException e){
			System.out.println(e);
		}
	}

	@Override
	public Collection<?> listCategoryProducts(Integer id) {
		@SuppressWarnings("unchecked")
		Collection<ProductJPA> allProductsByCategory = (Collection<ProductJPA>)entman.createQuery("FROM ProductJPA WHERE id = :id").setParameter("id", id).getResultList();
		//update ratings every time listing is invoked
		for (Iterator<ProductJPA> iter2 = allProductsByCategory.iterator(); iter2.hasNext();)
		{
			ProductJPA product2 = (ProductJPA) iter2.next();
			product2.setProductRatting(calculateProductRating(product2.getId()));
		}
		return allProductsByCategory;			
	}

	@Override
	public ProductJPA getProductInformation(Integer id) {
		ProductJPA product = entman.find(ProductJPA.class, id);
		return product;
	}
	
	public float calculateProductRating(Integer productId) {
		@SuppressWarnings("unchecked")
		Collection<ProductRattingJPA> allRatingsByProduct = (Collection<ProductRattingJPA>)entman.createQuery("FROM ProductRattingJPA b WHERE b.product.id  = :productId").setParameter("productId", productId).getResultList();
		float sum = 0.0f;
		int count = 0;
		if(allRatingsByProduct != null) {
			for (Iterator<ProductRattingJPA> iter2 = allRatingsByProduct.iterator(); iter2.hasNext();)
			{
				ProductRattingJPA productRatting2 = (ProductRattingJPA) iter2.next();
				sum += productRatting2.getRatting();
				count ++;
			}
			if(count == 0)
				return sum;
			else
				return sum / count;
		}
		
		return sum;
	}
	
	/**
	 * Method that returns Collection of all Items
	 * 
	 */
	@Override
	public Collection<ItemJPA> listAllItems() {
		@SuppressWarnings("unchecked")
		Collection<ItemJPA> allItems = entman.createQuery("from ItemJPA").getResultList();
		return allItems;
	}
	

	/**
	 * Method that adds a new item
	 */
	@Override
	public String addItem(String serialNumber, String itemStatus, Integer productId) throws PersistenceException {
		ItemJPA item = new ItemJPA();
		ProductJPA product = entman.find(ProductJPA.class, productId);
		item.setSerialNumber(serialNumber);
		item.setStatus(ItemStatus.valueOf(itemStatus.toUpperCase()));
		item.setProduct(product);
		entman.persist(item);
		int count = product.getAvailableItems();
		count++;
		product.setAvailableItems(count);
		entman.persist(product);
		return "Item successfully added";
	}

	@Override
	public void deleteItem(String serialNumber) {
		ItemJPA item = entman.find(ItemJPA.class, serialNumber);
		entman.remove(item);
		
	}

	@Override
	public String updateProductItem(String serialNumber, String status, Integer productId) {
		try {
			ItemJPA item = entman.find(ItemJPA.class, serialNumber);
			item.setStatus(ItemStatus.valueOf(status.toUpperCase()));
			item.setProduct(entman.find(ProductJPA.class, productId));
			entman.persist(item);
			return "Item successfully updated!";
		} catch (PersistenceException e) {
			System.out.println(e);
			return "Item NOT updated!";
		}
	}

	/**
	 * Method that lists all items of a product
	 */
	public Collection<ItemJPA> listProductsItems(Integer idProduct) throws PersistenceException {
		try {
			@SuppressWarnings("unchecked")
			Collection<ItemJPA> productsItemsList = (Collection<ItemJPA>)entman.createQuery("FROM ItemJPA WHERE product.id = :product").setParameter("product", idProduct).getResultList();
			System.out.println("hola");
			return productsItemsList;
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Method that adds a new brand
	 */
	public String addBrand(String name) throws PersistenceException {
		BrandJPA brand = new BrandJPA();
		brand.setName(name);
		entman.persist(brand);
		return "Brand successfully added";
	}
	
	
	/*
	 * Method that updates brand name
	 */
	@Override
	public void updateBrand(Integer brandId ,String name) {
		BrandJPA brand = entman.find(BrandJPA.class, brandId);
		brand.setName(name);
		entman.persist(brand);
	}
	
	/**
	 * Method that delete a brand
	 */
	@Override
	public void deleteBrand(Integer brandId, String name) {
		try {
			String query = "delete from practicalcase.brand where id  = '" + brandId + "';";
			entman.createNativeQuery(query).executeUpdate();		
		}
		catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that returns Collection of all brands
	 */
	@Override
	public Collection<BrandJPA> listAllBrands() {
		@SuppressWarnings("unchecked")
		Collection<BrandJPA> allBrands = entman.createQuery("from BrandJPA").getResultList();
		return allBrands;
	}

	/**
	 * Method that adds a new model
	 */
	@Override
	public String addModel(String name, int id) throws PersistenceException {
		ModelJPA model = new ModelJPA();
		BrandJPA brand = entman.find(BrandJPA.class, id);
		model.setName(name);
		model.setBrand(brand);
		entman.persist(model);
		return "Model successfully added";
		
	}

	@Override
	public void updateModel(Integer modelId, String name) {
		ModelJPA model = entman.find(ModelJPA.class, modelId);
		model.setName(name);
		entman.persist(model);
	}
	
	/**
	 * Method that delete a model
	 */
	@Override
	public void deleteModel(Integer brandId) {
		try {
			String query = "delete from practicalcase.model where id  = '" + brandId + "';";
			entman.createNativeQuery(query).executeUpdate();		
		}
		catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that returns Collection of all models
	 */
	@Override
	public Collection<ModelJPA> listAllModels() {
		@SuppressWarnings("unchecked")
		Collection<ModelJPA> allModels = entman.createQuery("from ModelJPA").getResultList();
		return allModels;
	}

	/*@Override
	public Collection<?> listProductAvailableItems(Integer productId, Date dataInit, Date dataFinal) {
		Collection<ItemJPA> result = new ArrayList<ItemJPA>();
		//get all items for a given product
		@SuppressWarnings("unchecked")
		Collection<ItemJPA> allItemsOfAProduct = (Collection<ItemJPA>)entman.createQuery("FROM ItemJPA b WHERE b.product.id  = :productId").setParameter("productId", productId).getResultList();
		for (Iterator<ItemJPA> iter2 = allItemsOfAProduct.iterator(); iter2.hasNext();)
		{
			ItemJPA item2 = (ItemJPA) iter2.next();
			//get all rents referencing this item
			@SuppressWarnings("unchecked")
			Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.item.serialnumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
			for (Iterator<RentItemJPA> iter3 = allRentsOfAnItem.iterator(); iter3.hasNext();)
			{
				RentItemJPA rentItem = (RentItemJPA) iter3.next();
				Date dataRentInit = rentItem.getRent().getFromDate();
				Date dataRentFinal = rentItem.getRent().getToDate();
				if((dataInit.before(dataRentInit) && dataFinal.before(dataRentInit))
						|| (dataInit.after(dataRentFinal) && dataFinal.after(dataRentFinal))){
					result.add(item2);
				}
			}
		}
		
		return result;
	}*/
	

	@Override
	public Collection<ItemJPA> listAvailableItems(Date dataInit, Date dataFinal)
	{
		Collection<ItemJPA> result = new ArrayList<ItemJPA>();
		//get all items for a given product
		@SuppressWarnings("unchecked")
		Collection<ItemJPA> allItems = (Collection<ItemJPA>)entman.createQuery("FROM ItemJPA").getResultList();
		for (Iterator<ItemJPA> iter2 = allItems.iterator(); iter2.hasNext();)
		{
			ItemJPA item2 = (ItemJPA) iter2.next();
			if(item2.getStatus() == ItemStatus.BROKEN) continue;
			//get all rents referencing this item
			Boolean foundItem = false;
			System.out.println("allItemsOfAProduct size::" + allItems.size());
			@SuppressWarnings("unchecked")
			Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.item.serialNumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
			Boolean itemToAdd = true;
			for (Iterator<RentItemJPA> iter3 = allRentsOfAnItem.iterator(); iter3.hasNext() && itemToAdd; )
			{
				RentItemJPA rentItem = (RentItemJPA) iter3.next();
				if(rentItem.getRent().getStatus() == 2) continue;
				System.out.println("allRentsOfAnItem size::" + allRentsOfAnItem.size());
				//if rent is cancelled do no
				/*if(rentItem.getRent().getStatus() == 2) {
					foundItem = true;
					result.add(item2);
				}
				else {*/
					Date dataRentInit = rentItem.getRent().getFromDate();
					Date dataRentFinal = rentItem.getRent().getToDate();
					/*if((dataInit.before(dataRentInit) && dataFinal.before(dataRentInit))
							|| (dataInit.after(dataRentFinal) && dataFinal.after(dataRentFinal))){*/
						
					System.out.println("dataRentInit::" + dataRentInit);
					System.out.println("dataRentFinal::" + dataRentFinal);
					System.out.println("dataInit::" + dataInit);
					System.out.println("dataFinal::" + dataFinal);
					//Boolean firstCheck = dataInit.after(dataRentInit) && dataInit.before(dataRentFinal);
					//Boolean secondCheck = dataFinal.after(dataRentInit) && dataFinal.before(dataRentFinal);
					Boolean firstCheck = dataInit.compareTo(dataRentInit) >= 0 && dataInit.compareTo(dataRentFinal) <= 0;
					Boolean secondCheck = dataFinal.compareTo(dataRentInit) >= 0 && dataFinal.compareTo(dataRentFinal) <= 0;
					System.out.println("firstCheck::" + firstCheck);
					System.out.println("secondCheck::" + secondCheck);
					/*if((dataInit.after(dataRentInit) && dataInit.before(dataRentFinal))
							|| (dataFinal.after(dataRentInit) && dataFinal.before(dataRentFinal))){*/
					if(firstCheck || secondCheck) {
						itemToAdd = false;
						break;
					}
				//}
			}
			
			if(allRentsOfAnItem.size() == 0 || itemToAdd) {
				foundItem = true;
				result.add(item2);
			}
		}
		
		return result;
	}
	
	@Override
	public Collection<?> listProductAvailableItems(Integer productId, Date dataInit, Date dataFinal) {
		Collection<ItemJPA> result = new ArrayList<ItemJPA>();
		//get all items for a given product
		@SuppressWarnings("unchecked")
		Collection<ItemJPA> allItemsOfAProduct = (Collection<ItemJPA>)entman.createQuery("FROM ItemJPA b WHERE b.product.id = :productId").setParameter("productId", productId).getResultList();
		Boolean foundItem = false;
		int availableItems = 0;
		for (Iterator<ItemJPA> iter2 = allItemsOfAProduct.iterator(); iter2.hasNext();)
		{
			ItemJPA item2 = (ItemJPA) iter2.next();
			if(item2.getStatus() == ItemStatus.BROKEN) continue;
			//get all rents referencing this item
			@SuppressWarnings("unchecked")
			Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.item.serialNumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
			//Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.serialnumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
			System.out.println("allRentsOfAnItem size::" + allRentsOfAnItem.size());
			Boolean itemToAdd = true;
			for (Iterator<RentItemJPA> iter3 = allRentsOfAnItem.iterator(); iter3.hasNext() && itemToAdd; )
			{
				RentItemJPA rentItem = (RentItemJPA) iter3.next();
				if(rentItem.getRent().getStatus() == 2) continue;
				//if rent is cancelled do no
				/*if(rentItem.getRent().getStatus() == 2) {
					foundItem = true;
					availableItems++;
					System.out.println("::foundItem::" + currentProduct.getId());												
				}
				else {*/
					Date dataRentInit = rentItem.getRent().getFromDate();
					Date dataRentFinal = rentItem.getRent().getToDate();
					Boolean firstCheck = dataInit.compareTo(dataRentInit) >= 0 && dataInit.compareTo(dataRentFinal) <= 0;
					Boolean secondCheck = dataFinal.compareTo(dataRentInit) >= 0 && dataFinal.compareTo(dataRentFinal) <= 0;
					System.out.println("firstCheck::" + firstCheck);
					System.out.println("secondCheck::" + secondCheck);
					if(firstCheck || secondCheck) {
						//availableItems++;
						//foundItem = true;
						itemToAdd = false;
						//System.out.println("::item not to add::" + currentProduct.getId());
					}
				//}
			}
			//if(allRentsOfAnItem.size() == 0) {
			if(itemToAdd) {
				foundItem = true;
				availableItems++;
				result.add(item2);
			}
		}
		
		return result;
	}
	
	@Override
	public Collection<?> listProductAvailableItems(Date dataInit, Date dataFinal) 
	{
		Collection<ProductJPA> result = new ArrayList<ProductJPA>();
		//get all items for a given product
		Collection<ProductJPA> allProducts = listAllProducts();
		System.out.println("all products size::" + allProducts.size());
		for (Iterator<ProductJPA> productIter = allProducts.iterator(); productIter.hasNext();) 
		{
			ProductJPA currentProduct = (ProductJPA) productIter.next();
			@SuppressWarnings("unchecked")
			Collection<ItemJPA> allItemsOfAProduct = (Collection<ItemJPA>)entman.createQuery("FROM ItemJPA b WHERE b.product.id  = :productId").setParameter("productId", currentProduct.getId()).getResultList();
			Boolean foundItem = false;
			int availableItems = 0;
			for (Iterator<ItemJPA> iter2 = allItemsOfAProduct.iterator(); iter2.hasNext();)
			{
				ItemJPA item2 = (ItemJPA) iter2.next();
				if(item2.getStatus() == ItemStatus.BROKEN) continue;
				//get all rents referencing this item
				@SuppressWarnings("unchecked")
				Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.item.serialNumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
				//Collection<RentItemJPA> allRentsOfAnItem = (Collection<RentItemJPA>)entman.createQuery("FROM RentItemJPA b WHERE b.serialnumber = :serialNumber").setParameter("serialNumber", item2.getSerialNumber()).getResultList();
				System.out.println("allRentsOfAnItem size::" + allRentsOfAnItem.size());
				Boolean itemToAdd = true;
				for (Iterator<RentItemJPA> iter3 = allRentsOfAnItem.iterator(); iter3.hasNext() && itemToAdd; )
				{
					RentItemJPA rentItem = (RentItemJPA) iter3.next();
					if(rentItem.getRent().getStatus() == 2) continue;
					//if rent is cancelled do no
					/*if(rentItem.getRent().getStatus() == 2) {
						foundItem = true;
						availableItems++;
						System.out.println("::foundItem::" + currentProduct.getId());												
					}
					else {*/
						Date dataRentInit = rentItem.getRent().getFromDate();
						Date dataRentFinal = rentItem.getRent().getToDate();
						Boolean firstCheck = dataInit.compareTo(dataRentInit) >= 0 && dataInit.compareTo(dataRentFinal) <= 0;
						Boolean secondCheck = dataFinal.compareTo(dataRentInit) >= 0 && dataFinal.compareTo(dataRentFinal) <= 0;
						System.out.println("firstCheck::" + firstCheck);
						System.out.println("secondCheck::" + secondCheck);
						if(firstCheck || secondCheck) {
							//availableItems++;
							//foundItem = true;
							itemToAdd = false;
							//System.out.println("::item not to add::" + currentProduct.getId());
						}
					//}
				}
				//if(allRentsOfAnItem.size() == 0) {
				if(itemToAdd) {
					foundItem = true;
					availableItems++;
					System.out.println("::foundItem::" + currentProduct.getId());
				}
			}
			if(foundItem) {
				currentProduct.setAvailableItems(availableItems);
				result.add(currentProduct);
				
			}
		}		
		return result;
	}
	
	@Override
	public BrandJPA getBrandById(Integer id) {
		return entman.find(BrandJPA.class, id);
	}
	
	@Override
	public ModelJPA getModelById(Integer id) {
		return entman.find(ModelJPA.class, id);
	}

	@Override
	public ItemJPA getProductItem(String serialNumber) {
		// TODO Auto-generated method stub
		ItemJPA item = entman.find(ItemJPA.class, serialNumber);
		return item;
	}
	
}
