package de.Ste3et_C0st.TerracottaPlacer.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TerracottaObj {

	private final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	private Material material;
	private boolean offset = false;
	
	
	public TerracottaObj(Material material, boolean offest){
		this.material = material;
		this.offset = offest;
	}
	
	public ItemStack getItemStack(){return this.getItemStack(1);}
	
	public ItemStack getItemStack(int amount){
		ItemStack i = new ItemStack(this.material);
		ItemMeta m = i.getItemMeta();
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if(offset){m.setDisplayName("§f[§cinvert§f] " + this.material.name().toLowerCase());}else{m.setDisplayName("§f" + this.material.name().toLowerCase());}
		i.setItemMeta(m);
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		i.setAmount(amount);
		return i;
	}
	
	public String getName(){
		if(!offset){
			return this.material.name().toLowerCase().split("_")[0];
		}else{
			return "invert_" + this.material.name().toLowerCase().split("_")[0];
		}
	}
	
	public void setCuboid(Location loc){
		BlockFace lookingFace = yawToFace(loc.getYaw());
		setBlock(new Relative(loc.clone(), 0, 0, 0, lookingFace), 3);
		setBlock(new Relative(loc.clone(), 0, 0, -1, lookingFace), 2);
		setBlock(new Relative(loc.clone(), 1, 0, -1, lookingFace), 1);
		setBlock(new Relative(loc.clone(), 1, 0, 0, lookingFace), 0);
	}
	
	@SuppressWarnings("deprecation")
	public void setBlock(Relative relative, int durability){
		Block b = relative.getSecondLocation().getBlock();
		if(b.getType().equals(Material.AIR)){
			b.setType(this.material);
			if(!this.offset){
				b.setData(getDurability(durability, relative.getFace().getOppositeFace()));
			}else{
				b.setData(getDurability(durability, relative.getFace()));
			}
			b.getState().update();
		}
	}
	
	private byte getDurability(int durability, BlockFace face){
		if(face.equals(BlockFace.NORTH)) return (byte) durability;
		if(face.equals(BlockFace.EAST)) return getRotation(durability, 1);
		if(face.equals(BlockFace.SOUTH)) return getRotation(durability, 2);
		if(face.equals(BlockFace.WEST)) return getRotation(durability, 3);
		return 0;
	}
	
	private byte getRotation(int durability, int roation){
		if(durability + roation <= 3){
			return (byte) (durability + roation);
		}else if(durability + roation == 4){
			return 0;
		}else if(durability + roation == 6){
			return 2;
		}else{
			return 1;
		}
	}
	
	private BlockFace yawToFace(float yaw) {return axis[Math.round(yaw / 90f) & 0x3];}

	public boolean isInverted() {
		return this.offset;
	}
}
