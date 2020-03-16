package plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class main extends JavaPlugin implements Listener, CommandExecutor{
	public int Hamount = 1;
	public LinkedList<home> homes = new LinkedList<home>();
	public LinkedList<Inventory> openedBPI = new LinkedList<Inventory>();
	public LinkedList<Player> playerBPI = new LinkedList<Player>();
	@Override
	public void onEnable() {
		getLogger().info("Test enabled");
		getServer().getPluginManager().registerEvents(this, this);


	}
	public Inventory stat=getServer().createInventory(null, 9, ChatColor.GREEN+"Status");
	public  ItemStack health;
	public ItemStack exit=new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
	public ItemStack air = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
	public Inventory trollINV=null;
	public Player trolledPlayer=null;
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player ) {
			Player p = (Player) sender;
			if(label.equalsIgnoreCase("stat")||label.equalsIgnoreCase("status")) {
				health=new ItemStack(Material.RED_DYE, Hamount);
				exit=new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
				ItemMeta me =exit.getItemMeta();
				ItemMeta m = air.getItemMeta();m.setDisplayName(" ");
				air.setItemMeta(m);
				me.setDisplayName(ChatColor.BOLD+""+ChatColor.DARK_RED+"EXIT");
				exit.setItemMeta(me);
				ItemMeta meta = health.getItemMeta();
				meta.setDisplayName(ChatColor.RED + "HEALTH");
				health.setItemMeta(meta);
				stat.setItem(0, air);
				stat.setItem(1, health);
				stat.setItem(2, air);
				stat.setItem(3, air);
				stat.setItem(4, air);
				stat.setItem(5, air);
				stat.setItem(6, air);
				stat.setItem(7, air);
				stat.setItem(8, exit);
				p.openInventory(stat);}
			if(label.equalsIgnoreCase("vanish")) {
				if(p.hasPermission("vanish")) {
					if(args[0].equalsIgnoreCase("true")) {
						p.sendMessage(ChatColor.YELLOW+"You are vanished");
						p.setCanPickupItems(false);
						p.hidePlayer(p);
						PotionEffect vanish = new PotionEffect(PotionEffectType.INVISIBILITY, 99999999,9999999);
						p.addPotionEffect(vanish);
					}
					if(args[0].equalsIgnoreCase("false")) {
						p.sendMessage(ChatColor.YELLOW+"You are not longer vanished");
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
						p.showPlayer(p);
						p.setCanPickupItems(true);
					}
				}
			}
			if(label.equalsIgnoreCase("sethome")) {
				if(args!=null) {
					String ar = args[0];
					p.sendMessage(ChatColor.YELLOW+"Home "+ar+" created!");
					addHome(p,p.getLocation(),ar);
				}
			}
			if(label.equalsIgnoreCase("removehome")||label.equalsIgnoreCase("remhome")) {
				if(args!=null) {
					String ar = args[0];
					p.sendMessage(ChatColor.YELLOW+"Home "+ar+" removed");
					removeHome(p,ar);
				}
			}
			if(label.equalsIgnoreCase("home")||label.equalsIgnoreCase("tphome")||label.equalsIgnoreCase("tphome")) {
				String ar = args[0];
				TPHome(p, ar,p.getLocation());
			}
			if(label.equalsIgnoreCase("crazyride")||label.equalsIgnoreCase("cr")) {
				Horse horseton = p.getWorld().spawn(p.getLocation(),Horse.class);
				horseton.setAdult();
				horseton.setColor(Color.WHITE);
				horseton.setOwner(p);
				horseton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999,10));
				horseton.setPassenger(p);
				horseton.getInventory().setSaddle(new ItemStack(Material.SADDLE));
				horseton.setMetadata("crhorse-"+horseton.getOwner().getName(),new FixedMetadataValue(this,"pl.hrs.cr.ow:"+p.getName()));
			}
			if(label.equalsIgnoreCase("troll")) {
				if(args.length>0) {
					String names="";
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						names=pl.getName();
						if(args[0].equals(names)) {
							ItemMeta m = air.getItemMeta();
							m.setDisplayName(" ");
							air.setItemMeta(m);
							trolledPlayer=getServer().getPlayerExact(args[0]);
							trollINV = getServer().createInventory(null, 27,ChatColor.GREEN+"Troll");
							ItemStack boom = new ItemStack(Material.TNT);
							ItemMeta meta1 =boom.getItemMeta();
							meta1.setDisplayName(ChatColor.RED+"BOOM");
							boom.setItemMeta(meta1);
							ItemStack loseConn = new ItemStack(Material.CUT_RED_SANDSTONE);
							ItemMeta meta2=loseConn.getItemMeta();
							meta2.setDisplayName(ChatColor.RED+"Lose connection");
							ItemStack strike = new ItemStack(Material.IRON_AXE);
							ItemMeta meta3 = strike.getItemMeta();
							meta3.setDisplayName(ChatColor.DARK_GRAY+"STRIKE");
							ItemStack oi = new ItemStack(Material.LEATHER_LEGGINGS);
							ItemMeta meta4 = oi.getItemMeta();
							meta4.setDisplayName(ChatColor.YELLOW+"open inventory");
							oi.setItemMeta(meta4);
							strike.setItemMeta(meta3);
							loseConn.setItemMeta(meta2);
							for(int i = 0; i<27;i++) {
								trollINV.setItem(i, air);
							}
							trollINV.setItem(0, boom);
							trollINV.setItem(9, loseConn);
							trollINV.setItem(18, strike);
							trollINV.setItem(1, oi);
							p.openInventory(trollINV);

						}
					}
				}

			}
			if(label.equalsIgnoreCase("openinv")) {
				if(args.length>0) {
					String names="";
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						names=pl.getName();
						if(args[0].equals(names)) {
							Player open = getServer().getPlayerExact(args[0]);
							Inventory invopen=getBPI(open);
							openedBPI.add(invopen);
							playerBPI.add(open);
							p.openInventory(invopen);
						}
					}
				}
			}
		}
		return true;

	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {

		Player p = event.getPlayer();
		Hamount=(int)p.getHealth()/2;

	}
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if(event.getEntityType().equals(EntityType.HORSE)) {
			Horse tempHorse = (Horse) event.getEntity();
			AnimalTamer owner = tempHorse.getOwner();
			if(event.getEntity().hasMetadata("crhorse-"+owner.getName())){
				event.getDrops().clear();
			}}
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(event.getPlayer().isOp()) {
			if(event.getMessage().charAt(0)=='*') {
				event.setCancelled(true);
				ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());
				for(int i=0;i<players.size();i++) {
					if(players.get(i).isOp()) {
						Player op = players.get(i);
						op.sendMessage(ChatColor.AQUA+"[AChat] "+event.getPlayer().getName()+ChatColor.RESET+event.getMessage());
					}
				}
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent event){
		InventoryClickEvent e = event;
		Player p = (Player)event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		try {
			if(openedBPI.get(0)!=null) {
				Inventory ifit = openedBPI.get(0);
				if(playerBPI.get(0)!=null) {
					Player owner = playerBPI.get(0);

					if(inv.equals(ifit)) {
						if(item.getItemMeta().getDisplayName().equals(ChatColor.AQUA+"open players inventory")) {
							p.closeInventory();
							p.openInventory(owner.getInventory());
							
						}
						if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE+"open players ender chest")) {
							p.closeInventory();
							p.openInventory(owner.getEnderChest());
						}
						e.setCancelled(true);
						openedBPI.remove(openedBPI.get(0));
						playerBPI.remove(playerBPI.get(0));
					}
				}
			}
		} catch(Exception exception) {

		}

		if(inv.equals(stat)) {
			if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BOLD+""+ChatColor.DARK_RED+"EXIT")) p.closeInventory();
			event.setCancelled(true);

		} else if(inv.equals(trollINV)) {

			if(item.getItemMeta().getDisplayName().equals(ChatColor.RED+"BOOM")) {
				trolledPlayer.setMaxHealth(40);
				trolledPlayer.setHealth(40);
				trolledPlayer.getLocation().getWorld().createExplosion(trolledPlayer.getLocation(), 4,false,false);
				trolledPlayer.setHealth(20);
				trolledPlayer.setMaxHealth(20);
			}
			if(item.getItemMeta().getDisplayName().equals(ChatColor.RED+"Lose connection")) {

				trolledPlayer.kickPlayer("Lost connection\n"+ChatColor.GRAY+"Recconect");
			}
			if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY+"STRIKE")) {

				trolledPlayer.getWorld().strikeLightning(trolledPlayer.getLocation());
			}
			if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"open inventory")) {
				p.closeInventory();
				p.openInventory(trolledPlayer.getInventory());
			}else {
				event.setCancelled(true);
				p.closeInventory();
			}



			trolledPlayer=null;
		}


	}




	public void addHome(Player p, Location loc, String name) {
		homes.add(new home(loc,p,name));
	}
	public void removeHome(Player sender,String name) {
		Player p = null;
		String n = "";
		Location loc = null;
		for(int i = 0; i<homes.size(); i++) {
			home temp = homes.get(i);
			p=temp.getPlayer();
			n=temp.getName();
			loc=temp.getLoc();
			if(sender.equals(p)) {
				if(name.equalsIgnoreCase(n)) {
					homes.remove(temp);
				}}
		}

	}
	public home TPHome(Player sender, String name, Location reset) {
		Player p=null;
		String n=null;


		home temp = null;
		for(int i = 0; i<homes.size(); i++) {
			temp = homes.get(i);
			p = temp.getPlayer();
			n=temp.getName();if(sender.equals(p)) {
				if(name.equalsIgnoreCase(n)) {
					temp.teleport();
					return temp;

				}
			}
		}

		return new home(reset,sender,name);

	}
	public class home{
		public Location loc;
		public Player p;
		public String name;
		public home(Location loc, Player p, String name) {
			this.loc=loc;
			this.p=p;
			this.name=name;
		}
		public void teleport() {
			p.teleport(loc);
			p.sendMessage(ChatColor.YELLOW+"Teleporting to "+getName()+"...");
		}
		public Player getPlayer() {
			return p;
		}
		public Location getLoc() {
			return loc;
		}
		public String getName() {
			return name;
		}
	}
	public Inventory getBPI(Player p) {
		ItemMeta m = air.getItemMeta();
		m.setDisplayName(" ");
		air.setItemMeta(m);
		Inventory inv = getServer().createInventory(null, 54, "BPI: "+ChatColor.YELLOW+p.getName()+ChatColor.RESET+" -overview");
		for(int i = 0; i<inv.getSize();i++) {
			inv.setItem(i, air);
		}
		if(p.getInventory().getHelmet()!=null) {
			inv.setItem(53, p.getInventory().getHelmet());
		}else {
			inv.setItem(53,  new ItemStack(Material.AIR));
		}
		if(p.getInventory().getChestplate()!=null) {
			inv.setItem(52, p.getInventory().getChestplate());
		} else {
			inv.setItem(52,  new ItemStack(Material.AIR));
		}
		if(p.getInventory().getLeggings()!=null) {
			inv.setItem(51, p.getInventory().getLeggings());
		} else {
			inv.setItem(51, new ItemStack(Material.AIR));
		}
		if(p.getInventory().getBoots()!=null) {
			inv.setItem(50, p.getInventory().getBoots());
		} else {
			inv.setItem(50,  new ItemStack(Material.AIR));
		}
		for(int i = 0;i<36;i++) {
			if(p.getInventory().getItem(i)!=null) {
				inv.setItem(i, p.getInventory().getItem(i));
			} else {
				inv.setItem(i, new ItemStack(Material.AIR));
			}
		}
		if(p.getInventory().getItemInOffHand()!=null) {
			inv.setItem(45, p.getInventory().getItemInOffHand());
		} else {
			inv.setItem(45,new ItemStack(Material.AIR));
		}
		ItemStack openpinv = new ItemStack(Material.CHEST);

		ItemMeta meta = openpinv.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA+"open players inventory");
		ItemStack enderchest = new ItemStack(Material.ENDER_CHEST);
		ItemMeta meta1=enderchest.getItemMeta();
		meta1.setDisplayName(ChatColor.DARK_PURPLE+"open players ender chest");
		enderchest.setItemMeta(meta1);
		openpinv.setItemMeta(meta);
		inv.setItem(47, openpinv);
		inv.setItem(48, enderchest);
		return inv;

	}


}

