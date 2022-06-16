package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.BookPage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

public class BookEntry {
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public ArrayList<BookPage> pages = new ArrayList<>();
    public EntryObjectSupplier objectSupplier = EntryObject::new;
    public BookEntry(String identifier, Item item, int xOffset, int yOffset) {
        this.identifier = identifier;
        this.iconStack = item.getDefaultInstance();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
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
        EntryObject getBookObject(BookEntry entry, int x, int y);
    }
}
