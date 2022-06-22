package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.BookPage;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class BookEntry {
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public final List<BookObject> children = new ArrayList<>();
    public final ResearchObject research;
    public ArrayList<BookPage> pages = new ArrayList<>();
    public EntryObjectSupplier objectSupplier = EntryObject::new;
    public BookEntry(String identifier, int xOffset, int yOffset, ResearchObject research) {
        this.identifier = identifier;
        this.iconStack = research.getIcon();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.research = research;
    }

    public BookEntry(String identifier, int xOffset, int yOffset, List<BookObject> child, ResearchObject research) {
        this.identifier = identifier;
        this.iconStack = research.getIcon();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.children.addAll(child);
        this.research = research;
    }

    public void addChild(BookObject child) {
        children.add(child);
    }

    public String translationKey(){
        return "astromancy.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey(){
        return "astromancy.gui.book.entry." + identifier + ".description";
    }

    public BookEntry addPage(BookPage page){
        if(page.isValid()){
            pages.add(page);
        }
        return this;
    }

    public BookEntry addModCompatPage(BookPage page, String modId){
        if(ModList.get().isLoaded(modId)){
            pages.add(page);
        }
        return this;
    }

    public BookEntry setObjectSupplier(EntryObjectSupplier supplier){
        this.objectSupplier = supplier;

        return this;
    }

    public interface EntryObjectSupplier {
        EntryObject getBookObject(BookEntry entry, int x, int y, List<BookObject> children, int localX, int localY, ResearchObject research);
    }
}
