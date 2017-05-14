package de.Ste3et_C0st.TerracottaPlacer.Main;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.Plugin;

public class LanguageManager{

	private String lang;
	private Plugin plugin;
	private HashMap<String, String> hash = new HashMap<String, String>();
	
	private HashMap<String, List<String>> invHashList = new HashMap<String, List<String>>();
	private HashMap<String, Material> invMatList = new HashMap<String, Material>();
	private HashMap<String, String> invStringList = new HashMap<String, String>();
	private HashMap<String, Short> invShortList = new HashMap<String, Short>();
	
	config c;
	FileConfiguration file;
	
	public LanguageManager(Plugin plugin, String lang){
		this.lang = lang;
		this.plugin = plugin;
		addDefault();
	}
	
	private void addDefault(){
		try{
			if(this.lang == null || this.lang.isEmpty()) lang = "EN_en";
			String s = "";
			
			if(plugin.getResource("language/" + lang + ".yml") != null){
				s = lang;
			}else{
				s = "EN_en";
			}
			
			c = new config(plugin);
			file = c.getConfig(lang, "/lang/");
			
			file.addDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("language/" + s + ".yml"))));
			file.options().copyDefaults(true);
			file.options().copyHeader(true);
			c.saveConfig(lang, file, "/lang/");
			
			for(String str : file.getConfigurationSection("message").getKeys(false)){
				String string = file.getString("message"+"."+str);
				hash.put(str, string);
			}
		}catch(NullPointerException ex){
			ex.printStackTrace();
			return;
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void addDefault(String a, Configuration defaults){
		c = new config(plugin);
		file = c.getConfig(lang + a, "/lang/");
		file.addDefaults(defaults);
		file.options().copyDefaults(true);
		c.saveConfig(lang + a, file, "/lang/");
		for(String str : file.getConfigurationSection("message").getKeys(false)){
			String string = file.getString("message"+"."+str);
			hash.put(str, string);
		}
	}
	
	public String getString(String a){
		if(hash.isEmpty()) return "§cHash is empty";
		if(!hash.containsKey(a)) return "§ckey not found";
		String b = hash.get(a);
		return ChatColor.translateAlternateColorCodes('&', b);
	}

	public List<String> getStringList(String a) {
		if(!invHashList.containsKey(a)){return null;}
		List<String> b = invHashList.get(a);
		Integer i = 0;
		for(String str : b){
			b.set(i, ChatColor.translateAlternateColorCodes('&', str));
			i++;
		}
		return b;
	}
	
	public String getName(String a){
		String b = invStringList.get(a);
		return ChatColor.translateAlternateColorCodes('&', b);
	}
	
	public Short getShort(String a){
		return invShortList.get(a);
	}

	public Material getMaterial(String a){
		return invMatList.get(a);
	}
}
