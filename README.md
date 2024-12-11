I AM NOT THE ORIGINAL AUTHOR.

BlueNeenja/bafomdad wrote Unique Crops. (https://github.com/bafomdad/uniquecrops)
This is only a fork so I could fix some bugs and use it in a modpack.

Fixes
* **Fixed crash when Collis seeds grow at y256+.**
* **Fixed crash when Fossura operates without a chest above it.**
* Standardized drops for mature vs. immature crops.
* Harvest Trap particle appearance and duration fixed (crudely). Also can't waste bait on it.
* Collecting a Dark Block leaves Bedrock behind based on the dimension's lower build limit instead of a fixed height (needed for taller Overworld — Petramia planting rules already did this).
* Mary Jane correctly only drops Cinderleaf if you're on fire. Also removed a secret extra harvest restriction.

Personal Tweaks
* Musica crop can now drop any disc on a full harvest too, not just on right-clicks. Added Otherside disc. Also grows slower, but multiples can grow from one Note Block.
* Redesigned Millennium crop. It now grows at 1/10th normal speed with a 1 minute minimum per stage. (Originally had a 10 minute minimum but then advanced to the next stage 2-3x as fast as normal, so crop accelerators had little effect.)
* Fossura won't dig blocks with the "forge:farmland" tag.
* Seeds and some crops are compostable.
* Revised some guidebook text.
* Redefined all crops internally as full-fledged CropBlocks. (Necessary so PneumaticCraft standard harvesting drones pick them.)

NOT FIXED / ToDo someday
* **Crucial pages in the guidebook are blank. This bug is only in the 1.18.2 version of Unique Crops and seems to be related to Patchouli integration.**
* Ender Lilies still randomly duplicate.
* Harvest Trap doesn't use its original custom particles.
