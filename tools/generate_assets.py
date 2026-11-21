"""Generates PLACEHOLDER textures, item models, lang, recipes and loot tables.
Re-run any time:  python3 tools/generate_assets.py
Replace the PNGs in src/main/resources/assets/hyrulecraft/textures with your own art."""
import json, os, random
from PIL import Image, ImageDraw

MOD = "hyrulecraft"
ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources")
A = os.path.join(ROOT, "assets", MOD)
D = os.path.join(ROOT, "data", MOD)
random.seed(7)

def w(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        json.dump(data, f, indent=2) if not isinstance(data, str) else f.write(data)

def save(img, *parts):
    p = os.path.join(A, "textures", *parts)
    os.makedirs(os.path.dirname(p), exist_ok=True)
    img.save(p)

def px(img, pts, c):
    for x, y in pts:
        if 0 <= x < img.width and 0 <= y < img.height: img.putpixel((x, y), c)

def new16(): return Image.new("RGBA", (16, 16), (0, 0, 0, 0))

# ---------- item icons ----------
def sword(blade, edge, guard, handle, thick=False):
    im = new16()
    for x in range(7, 15):
        px(im, [(x, 15 - x)], blade); px(im, [(x - 1, 15 - x)], edge)
        if thick: px(im, [(x, 16 - x), (x - 2, 15 - x)], blade)
    px(im, [(4, 7), (5, 8), (6, 9), (7, 10), (8, 11)], guard)
    px(im, [(5, 10), (4, 11), (3, 12), (2, 13)], handle); px(im, [(1, 14)], guard)
    return im

def club():
    im = new16(); br, dk = (139, 90, 43, 255), (92, 58, 28, 255)
    for i in range(12):
        px(im, [(13 - i, 2 + i), (12 - i, 2 + i)], br if i % 2 else dk)
    px(im, [(12, 3), (13, 5), (11, 4), (10, 6)], (200, 200, 190, 255))  # spikes
    return im

def shield():
    im = new16(); d = ImageDraw.Draw(im)
    d.polygon([(8, 0), (14, 3), (13, 10), (8, 15), (3, 10), (2, 3)], fill=(40, 70, 160, 255), outline=(220, 190, 60, 255))
    d.rectangle([7, 4, 8, 11], fill=(220, 190, 60, 255)); d.rectangle([5, 6, 10, 7], fill=(220, 190, 60, 255))
    return im

def hookshot():
    im = new16(); d = ImageDraw.Draw(im)
    d.rectangle([2, 9, 6, 13], fill=(90, 60, 40, 255)); d.rectangle([5, 7, 12, 9], fill=(130, 130, 140, 255))
    d.polygon([(12, 5), (15, 3), (14, 8), (12, 8)], fill=(190, 190, 200, 255)); px(im, [(8, 10), (9, 11)], (60, 60, 70, 255))
    return im

def ocarina():
    im = new16(); d = ImageDraw.Draw(im)
    d.ellipse([2, 4, 13, 12], fill=(50, 120, 220, 255), outline=(20, 60, 140, 255)); d.rectangle([11, 2, 13, 5], fill=(50, 120, 220, 255))
    px(im, [(5, 8), (7, 8), (9, 8), (6, 10), (8, 10)], (15, 30, 70, 255)); px(im, [(6, 6), (7, 6)], (200, 230, 255, 255))
    return im

def slate():
    im = new16(); d = ImageDraw.Draw(im)
    d.rectangle([3, 1, 12, 14], fill=(35, 40, 55, 255), outline=(15, 18, 28, 255))
    d.ellipse([6, 5, 9, 8], fill=(90, 220, 255, 255)); d.rectangle([5, 11, 10, 12], fill=(60, 170, 220, 255))
    return im

def leaf():
    im = new16(); d = ImageDraw.Draw(im)
    d.ellipse([2, 1, 13, 10], fill=(70, 170, 60, 255), outline=(30, 110, 40, 255)); d.line([8, 3, 8, 9], fill=(30, 110, 40, 255))
    d.line([8, 10, 6, 15], fill=(110, 80, 40, 255), width=1)
    return im

def heart():
    im = new16(); d = ImageDraw.Draw(im)
    d.ellipse([1, 2, 7, 8], fill=(220, 30, 50, 255)); d.ellipse([8, 2, 14, 8], fill=(220, 30, 50, 255))
    d.polygon([(1, 6), (14, 6), (8, 14)], fill=(220, 30, 50, 255)); px(im, [(4, 4), (3, 5)], (255, 170, 180, 255))
    d.rectangle([0, 0, 15, 0], fill=(0, 0, 0, 0))
    return im

def bottle():
    im = new16(); d = ImageDraw.Draw(im)
    d.rectangle([6, 1, 9, 3], fill=(150, 110, 70, 255)); d.rectangle([5, 4, 10, 13], fill=(190, 225, 240, 200), outline=(120, 160, 190, 255))
    d.ellipse([6, 7, 9, 10], fill=(255, 150, 220, 255)); px(im, [(5, 8), (10, 8), (7, 6)], (255, 255, 200, 255))
    return im

def rupee(c, edge):
    im = new16(); d = ImageDraw.Draw(im)
    d.polygon([(8, 0), (13, 4), (13, 11), (8, 15), (3, 11), (3, 4)], fill=c, outline=edge)
    d.line([8, 1, 8, 14], fill=edge); px(im, [(5, 5), (5, 6)], (255, 255, 255, 200))
    return im

def horn(c, size):
    im = new16(); d = ImageDraw.Draw(im)
    d.polygon([(4, 14), (8 - size, 4), (11, 14)], fill=c, outline=(80, 60, 40, 255)); return im

def jelly():
    im = new16(); d = ImageDraw.Draw(im)
    d.ellipse([3, 5, 13, 14], fill=(80, 150, 230, 230), outline=(40, 90, 170, 255)); px(im, [(6, 7), (5, 8)], (220, 240, 255, 255)); return im

def armor_icon(kind, c, dk, accent):
    im = new16(); d = ImageDraw.Draw(im)
    if kind == "helmet":
        d.rectangle([3, 3, 12, 8], fill=c, outline=dk); d.rectangle([3, 8, 5, 11], fill=c, outline=dk); d.rectangle([10, 8, 12, 11], fill=c, outline=dk)
        d.rectangle([3, 6, 12, 6], fill=accent)
    elif kind == "chestplate":
        d.rectangle([3, 3, 12, 14], fill=c, outline=dk); d.rectangle([0, 3, 3, 9], fill=c, outline=dk); d.rectangle([12, 3, 15, 9], fill=c, outline=dk)
        d.rectangle([6, 3, 9, 4], fill=(0, 0, 0, 0)); d.rectangle([3, 10, 12, 11], fill=accent)
    elif kind == "leggings":
        d.rectangle([4, 2, 11, 6], fill=c, outline=dk); d.rectangle([4, 6, 7, 14], fill=c, outline=dk); d.rectangle([8, 6, 11, 14], fill=c, outline=dk)
        d.rectangle([4, 4, 11, 4], fill=accent)
    else:
        d.rectangle([2, 7, 6, 13], fill=c, outline=dk); d.rectangle([9, 7, 13, 13], fill=c, outline=dk)
        d.rectangle([2, 12, 7, 13], fill=accent); d.rectangle([9, 12, 14, 13], fill=accent)
    return im

SETS = {  # base, dark, accent
    "hero":     ((60, 150, 60, 255),  (30, 90, 35, 255),  (200, 170, 90, 255)),
    "champion": ((40, 90, 190, 255),  (20, 45, 110, 255), (235, 235, 235, 255)),
    "zora":     ((70, 170, 200, 255), (30, 100, 135, 255), (240, 220, 120, 255)),
    "goron":    ((170, 90, 50, 255),  (100, 50, 30, 255), (240, 160, 60, 255)),
}
PIECES = ["helmet", "chestplate", "leggings", "boots"]

icons = {
    "master_sword": sword((200, 225, 245, 255), (255, 255, 255, 255), (60, 50, 160, 255), (50, 40, 120, 255)),
    "biggoron_sword": sword((150, 160, 175, 255), (210, 215, 225, 255), (120, 80, 40, 255), (90, 55, 30, 255), thick=True),
    "boko_club": club(), "hylian_shield": shield(), "hookshot": hookshot(), "ocarina_of_time": ocarina(),
    "sheikah_slate": slate(), "korok_leaf": leaf(), "heart_container": heart(), "fairy_bottle": bottle(),
    "rupee_green": rupee((60, 200, 90, 255), (20, 110, 50, 255)), "rupee_blue": rupee((60, 110, 230, 255), (20, 50, 140, 255)),
    "rupee_red": rupee((225, 55, 55, 255), (120, 20, 20, 255)), "rupee_purple": rupee((160, 70, 210, 255), (80, 30, 120, 255)),
    "rupee_gold": rupee((245, 200, 50, 255), (150, 110, 20, 255)),
    "bokoblin_horn": horn((230, 215, 180, 255), 0), "moblin_horn": horn((200, 190, 160, 255), 2),
    "lizalfos_horn": horn((170, 210, 110, 255), 1), "chuchu_jelly": jelly(),
}
for s, (c, dk, ac) in SETS.items():
    for p in PIECES: icons[f"{s}_{p}"] = armor_icon(p, c, dk, ac)

for name, im in icons.items(): save(im, "item", name + ".png")

# ---------- armor worn layers (placeholder fills; paint real UVs later) ----------
def layer(c, dk, ac, size):
    im = Image.new("RGBA", size, c)
    for _ in range(size[0] * size[1] // 6):
        x, y = random.randrange(size[0]), random.randrange(size[1]); im.putpixel((x, y), dk)
    d = ImageDraw.Draw(im); d.rectangle([0, size[1] // 2, size[0], size[1] // 2 + 1], fill=ac)
    return im
for s, (c, dk, ac) in SETS.items():
    save(layer(c, dk, ac, (64, 32)), "models", "armor", f"{s}_layer_1.png")
    save(layer(c, dk, ac, (64, 32)), "models", "armor", f"{s}_layer_2.png")

# ---------- entity textures ----------
def biped(skin, cloth, eye):
    im = Image.new("RGBA", (64, 64), skin); d = ImageDraw.Draw(im)
    for _ in range(500): im.putpixel((random.randrange(64), random.randrange(64)), tuple(max(0, v - 18) for v in skin[:3]) + (255,))
    d.rectangle([16, 20, 40, 32], fill=cloth); d.rectangle([0, 48, 63, 63], fill=cloth)  # torso/leg bands
    px(im, [(9, 11), (10, 11), (13, 11), (14, 11)], eye); d.rectangle([10, 14, 13, 14], fill=(40, 20, 20, 255))
    return im
save(biped((205, 70, 55, 255), (110, 70, 40, 255), (255, 230, 80, 255)), "entity", "bokoblin.png")
save(biped((120, 70, 150, 255), (70, 50, 40, 255), (255, 200, 60, 255)), "entity", "moblin.png")
save(biped((80, 150, 80, 255), (170, 150, 80, 255), (255, 240, 90, 255)), "entity", "lizalfos.png")
ch = Image.new("RGBA", (64, 32), (74, 143, 224, 255)); d = ImageDraw.Draw(ch)
d.rectangle([32, 0, 39, 7], fill=(10, 20, 50, 255)); d.rectangle([32, 8, 35, 9], fill=(10, 20, 50, 255))
save(ch, "entity", "chuchu.png")

# ---------- item models ----------
HANDHELD = {"master_sword", "biggoron_sword", "boko_club", "hylian_shield", "hookshot", "korok_leaf"}
for name in icons:
    w(os.path.join(A, "models", "item", name + ".json"),
      {"parent": "minecraft:item/handheld" if name in HANDHELD else "minecraft:item/generated",
       "textures": {"layer0": f"{MOD}:item/{name}"}})
for egg in ["bokoblin", "moblin", "lizalfos", "chuchu"]:
    w(os.path.join(A, "models", "item", f"{egg}_spawn_egg.json"), {"parent": "minecraft:item/template_spawn_egg"})

# ---------- lang ----------
lang = {"itemGroup.hyrulecraft.hyrule": "HyruleCraft",
  "item.hyrulecraft.master_sword": "Master Sword", "item.hyrulecraft.biggoron_sword": "Biggoron's Sword",
  "item.hyrulecraft.boko_club": "Boko Club", "item.hyrulecraft.hylian_shield": "Hylian Shield",
  "item.hyrulecraft.hookshot": "Hookshot", "item.hyrulecraft.ocarina_of_time": "Ocarina of Time",
  "item.hyrulecraft.sheikah_slate": "Sheikah Slate", "item.hyrulecraft.korok_leaf": "Korok Leaf",
  "item.hyrulecraft.heart_container": "Heart Container", "item.hyrulecraft.fairy_bottle": "Bottled Fairy",
  "item.hyrulecraft.rupee_green": "Green Rupee", "item.hyrulecraft.rupee_blue": "Blue Rupee",
  "item.hyrulecraft.rupee_red": "Red Rupee", "item.hyrulecraft.rupee_purple": "Purple Rupee", "item.hyrulecraft.rupee_gold": "Gold Rupee",
  "item.hyrulecraft.bokoblin_horn": "Bokoblin Horn", "item.hyrulecraft.moblin_horn": "Moblin Horn",
  "item.hyrulecraft.lizalfos_horn": "Lizalfos Horn", "item.hyrulecraft.chuchu_jelly": "Chuchu Jelly",
  "entity.hyrulecraft.bokoblin": "Bokoblin", "entity.hyrulecraft.moblin": "Moblin",
  "entity.hyrulecraft.lizalfos": "Lizalfos", "entity.hyrulecraft.chuchu": "Chuchu"}
for e in ["bokoblin", "moblin", "lizalfos", "chuchu"]:
    lang[f"item.hyrulecraft.{e}_spawn_egg"] = f"{e.capitalize()} Spawn Egg"
NAMES = {"hero": ("Hero's Cap", "Hero's Tunic", "Hero's Trousers", "Hero's Boots"),
         "champion": ("Champion's Cap", "Champion's Tunic", "Champion's Trousers", "Champion's Boots"),
         "zora": ("Zora Helm", "Zora Armor", "Zora Greaves", "Zora Boots"),
         "goron": ("Goron Helm", "Goron Tunic", "Goron Greaves", "Goron Boots")}
for s, names in NAMES.items():
    for p, n in zip(PIECES, names): lang[f"item.hyrulecraft.{s}_{p}"] = n
w(os.path.join(A, "lang", "en_us.json"), lang)

# ---------- recipes ----------
def recipe(name, pattern, key, count=1):
    w(os.path.join(D, "recipes", name + ".json"),
      {"type": "minecraft:crafting_shaped", "category": "equipment", "pattern": pattern,
       "key": {k: {"item": v} for k, v in key.items()}, "result": {"item": f"{MOD}:{name}", "count": count}})
mats = {"hero": "minecraft:emerald", "champion": "minecraft:diamond", "zora": "minecraft:prismarine_crystals", "goron": "minecraft:obsidian"}
pats = {"helmet": ["XXX", "X X"], "chestplate": ["X X", "XXX", "XXX"], "leggings": ["XXX", "X X", "X X"], "boots": ["X X", "X X"]}
for s, ing in mats.items():
    for p in PIECES: recipe(f"{s}_{p}", pats[p], {"X": ing})
recipe("master_sword", [" N ", "NSN", " T "], {"N": "minecraft:netherite_ingot", "S": "minecraft:nether_star", "T": "minecraft:stick"})
recipe("biggoron_sword", ["B", "B", "R"], {"B": "minecraft:diamond_block", "R": "minecraft:blaze_rod"})
recipe("boko_club", [" L ", " L ", " S "], {"L": "minecraft:oak_log", "S": "minecraft:stick"})
recipe("hylian_shield", ["III", "IDI", " I "], {"I": "minecraft:iron_ingot", "D": "minecraft:diamond"})
recipe("hookshot", ["  H", " C ", "I  "], {"H": "minecraft:tripwire_hook", "C": "minecraft:chain", "I": "minecraft:iron_ingot"})
recipe("ocarina_of_time", ["LLL", "LNL", " L "], {"L": "minecraft:lapis_lazuli", "N": "minecraft:note_block"})
recipe("sheikah_slate", ["OOO", "OEO", "ORO"], {"O": "minecraft:obsidian", "E": "minecraft:ender_eye", "R": "minecraft:redstone"})
recipe("korok_leaf", ["LLL", "LLL", " S "], {"L": "minecraft:oak_leaves", "S": "minecraft:stick"})
recipe("heart_container", ["RRR", "RAR", "RRR"], {"R": "minecraft:redstone_block", "A": "minecraft:enchanted_golden_apple"})
w(os.path.join(D, "recipes", "fairy_bottle.json"),
  {"type": "minecraft:crafting_shapeless", "category": "misc",
   "ingredients": [{"item": "minecraft:glass_bottle"}, {"item": "minecraft:glowstone_dust"}, {"item": "minecraft:golden_carrot"}],
   "result": {"item": f"{MOD}:fairy_bottle", "count": 1}})
# rupee exchange
w(os.path.join(D, "recipes", "rupee_blue.json"), {"type": "minecraft:crafting_shapeless", "category": "misc",
   "ingredients": [{"item": f"{MOD}:rupee_green"}] * 5, "result": {"item": f"{MOD}:rupee_blue", "count": 1}})
w(os.path.join(D, "recipes", "rupee_red.json"), {"type": "minecraft:crafting_shapeless", "category": "misc",
   "ingredients": [{"item": f"{MOD}:rupee_blue"}] * 4, "result": {"item": f"{MOD}:rupee_red", "count": 1}})

# ---------- loot tables ----------
def drop(item, lo, hi, loot=True):
    fn = [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": lo, "max": hi}}]
    if loot: fn.append({"function": "minecraft:looting_enchant", "count": {"min": 0, "max": 1}})
    return {"rolls": 1, "entries": [{"type": "minecraft:item", "name": f"{MOD}:{item}", "functions": fn}]}
def chance(item, p):
    return {"rolls": 1, "conditions": [{"condition": "minecraft:random_chance_with_looting", "chance": p, "looting_multiplier": 0.02}],
            "entries": [{"type": "minecraft:item", "name": f"{MOD}:{item}"}]}
loot = {
  "bokoblin": [drop("bokoblin_horn", 0, 1), drop("rupee_green", 1, 3), chance("fairy_bottle", 0.02)],
  "moblin":   [drop("moblin_horn", 0, 2), drop("rupee_blue", 1, 2), chance("rupee_red", 0.25), chance("heart_container", 0.01)],
  "lizalfos": [drop("lizalfos_horn", 0, 1), drop("rupee_green", 1, 4), chance("rupee_blue", 0.3)],
  "chuchu":   [drop("chuchu_jelly", 1, 2)],
}
for mob, pools in loot.items():
    w(os.path.join(D, "loot_tables", "entities", mob + ".json"), {"type": "minecraft:entity", "pools": pools})
print("assets generated")
