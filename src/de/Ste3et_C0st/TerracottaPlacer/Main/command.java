package de.Ste3et_C0st.TerracottaPlacer.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class command implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(cmd.getName().equalsIgnoreCase("terracotta")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("list")){
						String s = "avaible terracotta colors \n";
						s+="§m-------------------------------------------------§r\n";
						int i = 0;
						for(TerracottaObj obj : TerracottaPlacerMain.getInstance().getTerracottaList()){
							i++;
							s += "[" + i + "] " + obj.getName() + "\n";
						}
						s+="§m-------------------------------------------------§r\n";
						sender.sendMessage(s.substring(0, s.length()-1));
					}else{
						sendMessage(sender);
						return true;
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("give")){
						if(sender instanceof Player){
							if(((Player) sender).hasPermission("terracottaplacer.give")){
								if(TerracottaPlacerMain.getInstance().isTerracottaObj(args[1])){
									((HumanEntity) sender).getInventory().addItem(TerracottaPlacerMain.getInstance().getObj(args[1]).getItemStack());
								}else{
									sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("TerracottaNotFound").replace("#TERRACOTTA#", args[1]));
									return true;
								}
							}else{
								sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("NoPermissions"));
								return true;
							}
						}
					}else if(args[0].equalsIgnoreCase("help")){
						sendMessage(sender);
						return true;
					}else{
						sendMessage(sender);
						return true;
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("give")){
						if(sender instanceof Player){
							if(!((Player) sender).hasPermission("terracottaplacer.give.another")){
								sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("NoPermissions"));
								return true;
							}
						}
						
						if(TerracottaPlacerMain.getInstance().isTerracottaObj(args[1])){
							if(Bukkit.getPlayer(args[2])!=null){
								String msg = TerracottaPlacerMain.getInstance().getLangManager().getString("GivePlayer");
								msg = msg.replace("#PLAYER#", args[2]);
								msg = msg.replace("#TERRACOTTA#", args[1]);
								msg = msg.replace("#AMOUNT#", 1 + "");
								Bukkit.getPlayer(args[2]).getInventory().addItem(TerracottaPlacerMain.getInstance().getObj(args[1]).getItemStack());
							}else{
								sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("PlayerNotOnline").replace("#PLAYER#", args[2]));
								return true;
							}
						}else{
							sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("TerracottaNotFound").replace("#TERRACOTTA#", args[1]));
							return true;
						}
					}else{
						sendMessage(sender);
						return true;
					}
				}else if(args.length == 4){
					if(args[0].equalsIgnoreCase("give")){
						if(sender instanceof Player){
							if(!((Player) sender).hasPermission("terracottaplacer.give.another")){
								sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("NoPermissions"));
								return true;
							}
						}
						
						if(TerracottaPlacerMain.getInstance().isTerracottaObj(args[1])){
							if(Bukkit.getPlayer(args[2])!=null){
								int j = 1;
								try{
									j = Integer.parseInt(args[3]);
								}catch(Exception ex){
									sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("WrongArguments"));
									return true;
								}
								
								String msg = TerracottaPlacerMain.getInstance().getLangManager().getString("GivePlayer");
								msg = msg.replace("#PLAYER#", args[2]);
								msg = msg.replace("#TERRACOTTA#", args[1]);
								msg = msg.replace("#AMOUNT#", j + "");
								sender.sendMessage(msg);
								Bukkit.getPlayer(args[2]).getInventory().addItem(TerracottaPlacerMain.getInstance().getObj(args[1]).getItemStack(j));
							}else{
								sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("PlayerNotOnline").replace("#PLAYER#", args[2]));
								return true;
							}
						}else{
							sender.sendMessage(TerracottaPlacerMain.getInstance().getLangManager().getString("TerracottaNotFound").replace("#TERRACOTTA#", args[1]));
							return true;
						}
					}else{
						sendMessage(sender);
						return true;
					}
				}else{
					sendMessage(sender);
				}
			return true;
		}
		return false;
	}
	
	public void sendMessage(CommandSender sender){
		sender.sendMessage("§7/terracotta help");
		sender.sendMessage("§7/terracotta list");
		sender.sendMessage("§7/terracotta give <color>");
		sender.sendMessage("§7/terracotta give <color> <player>");
		sender.sendMessage("§7/terracotta give <color> <player> <amount>");
	}

}
