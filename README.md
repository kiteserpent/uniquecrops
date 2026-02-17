I AM NOT THE ORIGINAL AUTHOR.

BlueNeenja/bafomdad wrote Unique Crops (https://github.com/bafomdad/uniquecrops). This is an MIT License fork with bug fixes and some better inter-mod integration.

**Fixes**

* **Fixed crash when Collis seeds grow at y256+.**
* **Fixed crash when Fossura operates without a chest above it.**
* **Fixed crash with Easy Badge affecting Creepers.**
* **Fixed crash with Emblem of Transformation.**
* **Fixed client crash when charging Pixelsius Brush while playing on a server.**
* **Fixed broken Feroxia growth steps,** except brewing stand one, which is now internally disabled.
* Guidebook shows Feroxia growth steps, and updates if they change.
* Fixed crops that gave the crop instead of a seed when broken before maturing.
* Harvest Trap particle appearance and duration fixed (crudely). Also can't waste bait on it.
* Collecting a Dark Block leaves Bedrock behind based on the dimension's lower build limit instead of a fixed height (needed for taller Overworld — Petramia planting rules already did this).
* Mary Jane correctly only grows in the Nether and only drops Cinder Leaf if you're on fire. Removed a secret extra harvest restriction. ONLY bonemealable with Blaze Powder now, and the powder gets consumed.
* Spawners collected by a Precision Pick no longer overwrite blocks or duplicate when placed.
* Knowledge crops only read one book per growth tick instead of using up all of them.
* Fixed Sun Block.
* Ender Lilies no longer randomly overwrite crops or duplicate.
* Precision Crops only look fully grown when pickable and no longer give off redstone too early.
* Iron Stomach emblem buffs work reliably. Precision Gems are edible for diamond effects.
* Variant lily pads now place properly.

**Tweaks**

* **Fully expanded the in-game guidebooks with up-to-date info on all items, blocks and crops.**
* All crops that give a seed when right-clicked can give multiple seeds when broken, and vice versa.
* Musica crop can now drop any disc on a full harvest too, not just on right-clicks. Added Otherside disc. Also grows slower, but multiples can grow from one Note Block.
* Redesigned Millennium crop. It now grows at 1/10th normal speed with a 1 minute minimum per stage. (Originally had a 10 minute minimum but then advanced to the next stage 2-3x as fast as normal, so crop accelerators had little effect.)
* Fossura skips blocks with the "forge:farmland" tag. Also no longer digs at its own level, just lower.
* Seeds and some crops are compostable.
* Dyed Bone Meal works in Dispensers.
* Redefined all crops internally as full-fledged CropBlocks. (Lets more autoharvesters pick them.)

**NOT FIXED / ToDo someday**

* Harvest Trap doesn't use its original custom particles.
