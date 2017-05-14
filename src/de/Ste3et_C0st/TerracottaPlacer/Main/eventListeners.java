package de.Ste3et_C0st.TerracottaPlacer.Main;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class eventListeners implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(e.getClickedBlock()!=null){
				if(e.getPlayer().getInventory().getItemInMainHand()!=null&&TerracottaPlacerMain.getInstance().isItemStack(e.getPlayer().getInventory().getItemInMainHand())){
					e.setCancelled(true);
					Location loc = e.getClickedBlock().getRelative(BlockFace.UP).getLocation();
					loc.setYaw(e.getPlayer().getLocation().getYaw());
					TerracottaObj obj = TerracottaPlacerMain.getInstance().getObj(e.getItem());
					if(e.getPlayer().hasPermission("terracottaplacer.place." + obj.getName())){
						if(obj!=null){
							obj.setCuboid(loc);
							ItemStack stack = e.getPlayer().getInventory().getItemInMainHand().clone();
							if(stack.getAmount() - 1 > 0){
								stack.setAmount(stack.getAmount() - 1);
							}else{
								e.getPlayer().getInventory().remove(stack);
								return;
							}
							
							e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), stack);
						}
					}else{
						TerracottaPlacerMain.getInstance().getLangManager().getString("NoPermissions");
					}
				}
			}
		}
	}
	
	@EventHandler
	private void onCrafting(PrepareItemCraftEvent e){
		if(e.getInventory()==null) return;
		if(e.getInventory().getResult()==null) return;
		ItemStack is = e.getInventory().getResult().clone();
		is.setAmount(1);
		if(TerracottaPlacerMain.getInstance().isItemStack(is)){
			TerracottaObj obj = TerracottaPlacerMain.getInstance().getObj(is);
			if(!e.getView().getPlayer().hasPermission("terracottaplacer.craft." + obj.getName())){
				e.getInventory().setResult(null);
			}
		}
	}

}
