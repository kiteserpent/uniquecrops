I AM NOT THE ORIGINAL AUTHOR.

BlueNeenja/bafomdad wrote Unique Crops (https://github.com/bafomdad/uniquecrops). This is an MIT License fork with bug fixes and some better inter-mod integration.

Fixes

* Fixed crash when Collis seeds grow at y256+.
* Fixed crash when Fossura operates without a chest above it.
* Fixed client crash when charging Pixelsius Brush while playing on a server.
* Fixed broken Feroxia growth steps, except brewing stand one, which is now internally disabled.
* Guidebook shows Feroxia growth steps, and updates if they change.
* Standardized drops for mature vs. immature crops, and made extra seeds consistent between right- and left-clicking.
* Harvest Trap particle appearance and duration fixed (crudely). Also can't waste bait on it.
* Collecting a Dark Block leaves Bedrock behind based on the dimension's lower build limit instead of a fixed height (needed for taller Overworld — Petramia planting rules already did this).
* Mary Jane correctly only plants in the Nether and only drops Cinder Leaf if you're on fire. Removed a secret extra harvest restriction. ONLY bonemealable with Blaze Powder now, and the powder gets consumed.
* Knowledge crops only read one book per growth tick instead of using up all of them.
* Fixed Sun Block.
* Ender Lilies no longer randomly overwrite crops or duplicate.

Tweaks

* Musica crop can now drop any disc on a full harvest too, not just on right-clicks. Added Otherside disc. Also grows slower, but multiples can grow from one Note Block.
* Redesigned Millennium crop. It now grows at 1/10th normal speed with a 1 minute minimum per stage. (Originally had a 10 minute minimum but then advanced to the next stage 2-3x as fast as normal, so crop accelerators had little effect.)
* Fossura won't dig blocks with the "forge:farmland" tag.
* Seeds and some crops are compostable.
* Revised some guidebook text.
* Redefined all crops internally as full-fledged CropBlocks. (Lets more mods pick them.)

NOT FIXED / ToDo someday

* Harvest Trap doesn't use its original custom particles.
