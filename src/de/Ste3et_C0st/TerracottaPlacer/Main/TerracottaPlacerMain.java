package de.Ste3et_C0st.TerracottaPlacer.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class TerracottaPlacerMain extends JavaPlugin implements Listener{
	
	private List<TerracottaObj> list = Arrays.asList(
			new TerracottaObj(Material.WHITE_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.ORANGE_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.MAGENTA_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.YELLOW_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.LIME_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.PINK_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.GRAY_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.SILVER_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.CYAN_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.PURPLE_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.BLUE_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.BROWN_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.GREEN_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.RED_GLAZED_TERRACOTTA, false),
			new TerracottaObj(Material.BLACK_GLAZED_TERRACOTTA, false),
			
			new TerracottaObj(Material.WHITE_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.ORANGE_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.MAGENTA_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.YELLOW_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.LIME_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.PINK_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.GRAY_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.SILVER_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.CYAN_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.PURPLE_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.BLUE_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.BROWN_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.GREEN_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.RED_GLAZED_TERRACOTTA, true),
			new TerracottaObj(Material.BLACK_GLAZED_TERRACOTTA, true));
	public LanguageManager langManager;
	private static TerracottaPlacerMain instance;
	public List<TerracottaObj> getTerracottaList(){return this.list;}
	public static TerracottaPlacerMain getInstance(){return instance;}
	public LanguageManager getLangManager(){return this.langManager;}
	
	@Override
	public void onEnable(){
		getConfig().addDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("config.yml"))));
		getConfig().options().copyDefaults(true);
		getConfig().options().copyHeader(true);
		saveConfig();
		
		Bukkit.getPluginManager().registerEvents(new eventListeners(), this);
		getCommand("terracotta").setExecutor(new command());
		getCommand("terracotta").setTabCompleter(new TabCompleterHandler());
		this.langManager = new LanguageManager(this, getConfig().getString("config.Language", "EN_en"));
		instance = this;
		registerRecipe();
	}
	
	@SuppressWarnings("deprecation")
	private void registerRecipe(){
		boolean normal = getConfig().getBoolean("config.craftingNormalEnable", true);
		boolean invert = getConfig().getBoolean("config.craftingInvertEnable", true);
		
		for(TerracottaObj obj : this.list){
			if(!obj.isInverted()){
				if(normal){
					MaterialData data = new MaterialData(obj.getItemStack().getType());
					ShapedRecipe recipe = new ShapedRecipe(obj.getItemStack()).shape("XX", "XX");
					recipe.setIngredient('X', data);
					Bukkit.getServer().addRecipe(recipe);
				}
			}else{
				if(invert){
					MaterialData data = new MaterialData(obj.getItemStack().getType());
					ShapedRecipe recipe = new ShapedRecipe(obj.getItemStack()).shape("XXO", "XXI");
					recipe.setIngredient('X', data);
					recipe.setIngredient('O', Material.valueOf(getConfig().getString("config.craftingMaterialForInvert", "FLINT")));
					recipe.setIngredient('I', Material.AIR);
					Bukkit.getServer().addRecipe(recipe);
				}
			}
		}
	}
	
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean isItemStack(ItemStack stack){
		ItemStack clone = stack.clone();
		clone.setAmount(1);
		for(TerracottaObj obj : this.list){
			if(obj.getItemStack().equals(clone)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isTerracottaObj(String name){
		if(name.equalsIgnoreCase("random")) return true;
		for(TerracottaObj obj : this.list){
			if(obj.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public TerracottaObj getObj(ItemStack stack){
		ItemStack clone = stack.clone();
		clone.setAmount(1);
		for(TerracottaObj obj : this.list){
			if(obj.getItemStack().equals(clone)){
				return obj;
			}
		}
		return null;
	}
	
	public TerracottaObj getObj(String name){
		if(name.equalsIgnoreCase("random")){
			int i = randInt(0, this.list.size() - 1);
			return this.list.get(i);
		}
		for(TerracottaObj obj : this.list){
			if(obj.getName().equalsIgnoreCase(name)){
				return obj;
			}
		}
		return null;
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = new Random().nextInt((max - min) + 1) + min;
	    return randomNum;
	}

}