package de.Ste3et_C0st.TerracottaPlacer.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompleterHandler implements TabCompleter{

	private List<String> str = Arrays.asList("help", "list", "give");
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2,String[] args) {
		if(sender instanceof Player){
			if(cmd!=null&&cmd.getName().equalsIgnoreCase("terracotta")){
				if(args.length==1){
					return str;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("give")){
					List<String> str = new ArrayList<String>();
					for(TerracottaObj obj : TerracottaPlacerMain.getInstance().getTerracottaList()){
						str.add(obj.getName());
					}
					str.add("random");
					return str;
				}
			}
		}
		return null;
	}

}
