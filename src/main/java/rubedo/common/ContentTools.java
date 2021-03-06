package rubedo.common;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.client.ToolBaseRenderer;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.common.materials.MaterialMultiItem.Wood;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ItemAutoRepair;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import rubedo.items.tools.recipes.ToolEnchantmentRecipes;
import rubedo.items.tools.recipes.ToolRepairRecipes;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentTools extends ContentMultiItem<ToolBase> implements
		IContent {

	public Map<MaterialMultiItem, String> VanillaToolMaterials;

	public static class Config {
		// Default Values
		public static boolean addUnrefinedRecipes = true;
	}

	public static ItemAutoRepair itemAutoRepair;

	protected ContentTools() {
		super(ContentTools.class);

		// Tool kinds
		Set<Class<? extends ToolBase>> toolKinds = new LinkedHashSet<Class<? extends ToolBase>>();

		toolKinds.add(ToolSword.class);
		toolKinds.add(ToolPickaxe.class);
		toolKinds.add(ToolShovel.class);
		toolKinds.add(ToolAxe.class);
		toolKinds.add(ToolScythe.class);

		this.setKinds(toolKinds);
	}

	private void initializeToolMaterials() {
		// Tool materials
		Set<Class<? extends MaterialMultiItem>> toolMaterials = new LinkedHashSet<Class<? extends MaterialMultiItem>>();

		toolMaterials.add(MaterialMultiItem.Wood.class);
		toolMaterials.add(MaterialMultiItem.Flint.class);
		toolMaterials.add(MaterialMultiItem.Copper.class);
		toolMaterials.add(MaterialMultiItem.Iron.class);
		toolMaterials.add(MaterialMultiItem.Gold.class);
		toolMaterials.add(MaterialMultiItem.Orichalcum.class);
		toolMaterials.add(MaterialMultiItem.Silver.class);
		toolMaterials.add(MaterialMultiItem.Steel.class);
		toolMaterials.add(MaterialMultiItem.Mythril.class);
		toolMaterials.add(MaterialMultiItem.Hepatizon.class);

		// Rods
		toolMaterials.add(MaterialMultiItem.Bone.class);
		toolMaterials.add(MaterialMultiItem.Leather.class);
		toolMaterials.add(MaterialMultiItem.Blazerod.class);

		this.setMaterials(toolMaterials);

		// Vanilla tool materials
		this.VanillaToolMaterials = new LinkedHashMap<MaterialMultiItem, String>();

		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialMultiItem.Wood.class), "wooden");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialMultiItem.Flint.class), "stone");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialMultiItem.Iron.class), "iron");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialMultiItem.Gold.class), "golden");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialMultiItem.Steel.class), "diamond");
	}

	@Override
	public void config(Configuration config) {
		Config.addUnrefinedRecipes = config.get("tools", "AddUnrefinedRecipes",
				Config.addUnrefinedRecipes,
				"Add recipes for unrefined tool heads?").getBoolean(
				Config.addUnrefinedRecipes);
	}

	public void registerMaterial(
			Class<? extends MaterialMultiItem> materialClass) {
		if (this.getMaterial(materialClass) == null)
			this.addMaterial(materialClass);

		MaterialMultiItem material = this.getMaterial(materialClass);

		boolean registerVanillaTools = !ContentVanilla.Config.replaceVanillaTools;

		// For all tool heads
		if (material.headMaterial != null) {
			// Get all tool kinds
			for (ToolBase kind : this.getItems()) {
				// Check if we need to exclude vanilla materials
				if (registerVanillaTools
						|| !this.VanillaToolMaterials.containsKey(material)) {
					String name = kind.getName() + "_head_" + material.name;
					Item item = ItemToolHead.getHeadMap().get(name);
					GameRegistry.registerItem(item, name);
					material.setToolHead(kind.getName(), new ItemStack(item, 1));
				}
			}
			if (!material.isColdWorkable) {
				String name = "_head_" + material.name;
				Item item = ItemToolHead.getHeadMap().get("unrefined" + name);
				GameRegistry.registerItem(item, "unrefined" + name);
				material.setToolHead("unrefined", new ItemStack(item, 1));
				item = ItemToolHead.getHeadMap().get("hot" + name);
				GameRegistry.registerItem(item, "hot" + name);
				material.setToolHead("hot", new ItemStack(item, 1));
			}
		}
	}

	@Override
	public void registerBase() {
		super.registerBase();

		this.initializeToolMaterials();

		for (ToolBase kind : this.getItems()) {
			ToolBaseRenderer renderer = new ToolBaseRenderer();
			MinecraftForgeClient.registerItemRenderer(kind, renderer);
		}

		// Get all materials
		for (MaterialMultiItem material : this.getMaterials()) {
			this.registerMaterial(material.getClass());
		}

		itemAutoRepair = new ItemAutoRepair();
		GameRegistry.registerItem(itemAutoRepair, "itemautorepair");
	}

	@Override
	public void registerDerivatives() {
		this.registerToolRecipes();

		this.registerAutoRepairRecipe();
	}

	@Override
	public void tweak() {
		MinecraftForge.EVENT_BUS.register(new ToolEnchantmentRecipes());
	}

	private void registerAutoRepairRecipe() {
		// GameRegistry.addRecipe(new ShapedOreRecipe(new
		// ItemStack(Items.apple), " I ", "IGI", " I ", 'I',
		// "ingotGold", 'G', "ingotIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				ContentTools.itemAutoRepair), " I ", "IGI", " I ", 'I',
				"ingotGold", 'G', "gemCopper"));
	}

	private void registerToolRecipes() {
		// GameRegistry.addRecipe(new ToolEnchantmentRecipes());
		GameRegistry.addRecipe(new ToolRepairRecipes());
		RecipeSorter.register("rubedo:toolrepair", ToolRepairRecipes.class,
				Category.SHAPELESS, "");

		// Tool recipes
		for (MaterialMultiItem material : this.getMaterials()) {
			if (material.headMaterial != null) {
				// Tool Head Recipes
				Object headMaterial = material.headMaterial;
				if (OreDictionary.getOreIDs(material.headMaterial).length > 0)
					headMaterial = OreDictionary.getOreName(OreDictionary
							.getOreIDs(material.headMaterial)[0]);

				if (material.isColdWorkable && !(material instanceof Wood)) {
					// Sword heads
					GameRegistry
							.addRecipe(new ShapedOreRecipe(material
									.getToolHead("sword"), "X", "X", 'X',
									headMaterial));
					// Shovel heads
					GameRegistry.addRecipe(new ShapedOreRecipe(material
							.getToolHead("shovel"), "XX", "XX", 'X',
							headMaterial));
					// Axe heads
					GameRegistry.addRecipe(new ShapedOreRecipe(material
							.getToolHead("axe"), true, "XX", " X", 'X',
							headMaterial));
					// Scythe heads
					GameRegistry.addRecipe(new ShapedOreRecipe(material
							.getToolHead("scythe"), true, "XXX", "X  ", 'X',
							headMaterial));
					// Pick heads
					GameRegistry.addRecipe(new ShapedOreRecipe(material
							.getToolHead("pickaxe"), "XXX", 'X', headMaterial));
				} else if (Config.addUnrefinedRecipes
						&& !(material instanceof Wood)) {
					GameRegistry.addRecipe(new ShapelessOreRecipe(material
							.getToolHead("unrefined"), headMaterial,
							headMaterial, headMaterial));
				}

				// Tool recipes
				for (MaterialMultiItem materialRod : this.getMaterials()) {
					if (materialRod.rodMaterial != null) {

						Object rodMaterial = materialRod.rodMaterial;
						if (OreDictionary.getOreIDs(materialRod.rodMaterial).length > 0)
							rodMaterial = OreDictionary
									.getOreName(OreDictionary
											.getOreIDs(materialRod.rodMaterial)[0]);

						for (MaterialMultiItem materialCap : this
								.getMaterials()) {
							if (materialCap.capMaterial != null) {

								Object capMaterial = materialCap.capMaterial;
								if (OreDictionary
										.getOreIDs(materialCap.capMaterial).length > 0)
									capMaterial = OreDictionary
											.getOreName(OreDictionary
													.getOreIDs(materialCap.capMaterial)[0]);

								for (Class<? extends ToolBase> kind : this
										.getKinds()) {
									ItemStack tool = this.getItem(kind)
											.buildTool(material, materialRod,
													materialCap);
									ItemStack toolHead = material
											.getToolHead(this.getItem(kind)
													.getName());
									GameRegistry
											.addRecipe(new ShapelessOreRecipe(
													tool, toolHead,
													rodMaterial, capMaterial));
								}
							}
						}
					}
				}
			}
		}
	}
}
