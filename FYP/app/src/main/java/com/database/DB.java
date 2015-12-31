package com.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Region;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class DB {
    String barcode;
    String pName = "nothing";
    Layer1DBAdapter mDbAdapter;
    Context mainContext;
    Long mRowId;
    private String parsedHtmlNode = null;

    historyDB HistoryDbObject;

    public DB(Context mainCtx){

        this.mainContext =mainCtx;
        mDbAdapter = new Layer1DBAdapter(mainContext);
        mDbAdapter.open();

        HistoryDbObject=  new historyDB(mainCtx);

       // Parse.enableLocalDatastore(mainContext);
       // Parse.initialize(mainContext, "P41DF2gmqCqpx4l130YCTKDmUKkr6qAiV12dzPH3", "b3Hyzg2x3iLBsIbRTAzAcnS49WqWQR1wHohWTyAS");
        popuateSampleData();
    }

    private void getProductName(String barcode) {
       //* this commented code will be our layer 1 of local database
        boolean previousLayerFailed = true;

        try {
            Cursor c = mDbAdapter.getItemByBarcode(barcode);
            if (c.getCount() == 0) {
                //Toast.makeText(mainContext.getApplicationContext(), "Wrong barcode",
                  //      Toast.LENGTH_LONG).show();

            } else {
                c = mDbAdapter.getProductName(barcode);

                if (c.getCount() == 0) {

                    //Toast.makeText(mainContext.getApplicationContext(), "Some Error",
                      //      Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mainContext.getApplicationContext(), "Product Found",
                           Toast.LENGTH_LONG).show();

                    pName = c.getString(0);
                    previousLayerFailed = false;
                    return;
                }

            }
        }catch(Exception e){e.printStackTrace();}


        // -----------Layer2
        if(previousLayerFailed) {

            UPCDatabase upcLayer2 = new UPCDatabase();
            upcLayer2.barcode = barcode;
            upcLayer2.execute();

            try {
                upcLayer2.get();
                pName = parsedHtmlNode;
                previousLayerFailed = false;
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();previousLayerFailed = true;
            } catch (ExecutionException e) {
                e.printStackTrace();previousLayerFailed = true;
            }
            // -----------Layer2 end
        }
        if(previousLayerFailed){}


    }

    private void popuateSampleData(){
        Cursor c = mDbAdapter.GetCount();
        if(c.getCount() <= 1100){

            registerProduct("012000014338", "Aquafina Mineral Water", "500ml");
            registerProduct("012000002090","Aquafina Mineral Water 1.5tr","1500ML");
            registerProduct("12000002946","Pepsi Drink tr","1l");
            registerProduct("12000004070","Pepsi Drink Jumbo 2.tr","25l");
            registerProduct("12000014338","Aquafina Mineral Water ","500ml");
            registerProduct("12000022784","7UP Drink Can Free ","250ml");
            registerProduct("12000034220","Sting Energy Drink Can Berry Blast ","250ml");
            registerProduct("12000800184","Mirinda Drink Orange 1.tr","5l");
            registerProduct("12000800337","7UP Drink 1.tr","5l");
            registerProduct("12000801402","7UP Drink Free 1.tr","5l");
            registerProduct("12000801754","Pepsi Drink ","500ml");
            registerProduct("12000801914","Pepsi Drink Can Diet ","250ml");
            registerProduct("1358946337964","Bake Parlor Spaghetti Box ","450g");
            registerProduct("1358946338022","Bake Parlor Spaghetti Fancy ","500g");
            registerProduct("1358946338190","Bake Parlor Egg Noodles Chinese ","227g");
            registerProduct("1358946338268","Bake Parlor Lasagne ","400g");
            registerProduct("1358946338336","Bake Parlor Macaroni Long Box ","450g");
            registerProduct("1358946338404","Bake Parlor Macaroni Elbow Box ","400g");
            registerProduct("1358946338572","Bake Parlor Macaroni Elbow ","400g");
            registerProduct("1358946338886","Bake Parlor Macaroni Spirals ","400g");
            registerProduct("1358946338992","Bake Parlor Macaroni Twisted ","400g");
            registerProduct("1358946339012","Bake Parlor Macaroni Shell ","400g");
            registerProduct("1358946339180","Bake Parlor Macaroni Penne ","400g");
            registerProduct("1358946339746","Bake Parlor Spaghetti Meat Ball ","250g");
            registerProduct("1358946340070","Bake Parlor Vermicelli Coloured ","400g");
            registerProduct("1358946340148","Bake Parlor Vermicelli Coloured ","200g");
            registerProduct("1358946340384","Bake Parlor Vermicelli U Shape ","150g");
            registerProduct("1358946343453","Bake Parlor Macaroni BBQ ","250g");
            registerProduct("1358946343668","Bake Parlor Macaroni Tikka ","250g");
            registerProduct("1358946345716","Bake Parlor Pasta Vegetable 3 Color ","400g");
            registerProduct("1358946345969","Bake Parlor Macaroni Balti ","250g");
            registerProduct("1364008","Heinz Tomato Ketchup ","567g");
            registerProduct("21160967","Finis Phenyle Daily Mop ","225ml");
            registerProduct("21161001","Finis Phenyle Daily Mop ","425ml");
            registerProduct("21161155","Finis Phenyle Daily Mop 2.tr","75l");
            registerProduct("21200000003","Scotch Brite Celluosic Heavy Duty"," ");
            registerProduct("21200000010","Scotch Brite Celluosic Delicate Care"," ");
            registerProduct("21200001833","Scotch Brite Celluosic Non-Scratch"," ");
            registerProduct("21200510038","Scotch Brite Kitchen Gloves Light Duty Large Pink"," ");
            registerProduct("21200510069","Scotch Brite Kitchen Gloves Large"," ");
            registerProduct("2563874554409","Bake Parlor Macaroni Fusilli ","400g");
            registerProduct("2572242274627","Bake Parlor Macaroni Achari ","250g");
            registerProduct("2572422862224","Bake Parlor Macaroni Chat ","250g");
            registerProduct("2572457722500","Bake Parlor Spaghetti Chicken Chilli ","250g");
            registerProduct("2575253739468","Bake Parlor Macaroni Jalfrezi ","250g");
            registerProduct("2575272462507","Bake Parlor Lasagne Chicken ","250g");
            registerProduct("2576677722500","Bake Parlor Macaroni Chicken Manchurian ","250g");
            registerProduct("2577266726220","Bake Parlor Macaroni Samosa ","250g");
            registerProduct("2577427445564","Bake Parlor Macaroni Shashlik ","250g");
            registerProduct("300875101667","Enfamil Powder Milk A+1 ","800g");
            registerProduct("300875102091","Enfa Mama Powder Milk Vanilla A+ ","400g");
            registerProduct("300875102107","Enfa Mama Powder Milk Chocolate A+ ","400g");
            registerProduct("3014260243531","Gillette Mach3 Cartridges (Pack of 4)"," ");
            registerProduct("3014260243548","Gillette Mach3 Cartridges (Pack of 8)"," ");
            registerProduct("3014260251970","Gillette Mach3 Cartridges (Pack of 2)"," ");
            registerProduct("3014260331306","Gillette Mach3 Cartridges Turbo (Pack of 4)"," ");
            registerProduct("3014260823801","Oral-B Toothbrush ProExpert Antibacterial Medium 40"," ");
            registerProduct("3245421161573","Spontex Gloves Daily Use Medium"," ");
            registerProduct("3245421402577","Spontex Gloves Washup Medium"," ");
            registerProduct("3245421944169","Spontex Gloves Sensitive Large"," ");
            registerProduct("3245422135443","Spontex Gloves Trios Medium"," ");
            registerProduct("3245422135467","Spontex Gloves Trios Large"," ");
            registerProduct("3384121115094","Spontex Micro Fiber Cloth Multi Purpose"," ");
            registerProduct("3384121223027","Spontex Micro Fiber Cloth (Pack of 4)"," ");
            registerProduct("3384121512039","Spontex Scouring Sponges Handy Grip"," ");
            registerProduct("3384121512190","Spontex Leminate Handy Grip (Pack of 2)"," ");
            registerProduct("3384121515191","Spontex Leminate (Pack of 10)"," ");
            registerProduct("3384122191196","Spontex Sponge Cloth (Pack of 3)"," ");
            registerProduct("3384123088006","Spontex Leminate Gratounett"," ");
            registerProduct("3384126600090","Spontex Stainless Steel Spiral"," ");
            registerProduct("3384126600205","Spontex Stainless Steel Scourer Expert"," ");
            registerProduct("3384126600267","Spontex Stainless Steel Spiral (Pack of 2)"," ");
            registerProduct("3384127008017","Spontex Draining Mat Egouttoir"," ");
            registerProduct("3384127022013","Spontex Sponge Cloth (Pack of 8)"," ");
            registerProduct("3384129002389","Spontex Floor Squeegee 33cm"," ");
            registerProduct("34649795461","Roomi Naphthalene Balls Colored"," ");
            registerProduct("3546974977131","Guard Rice Easy Cook Sella ","1kg");
            registerProduct("3574660132434","Johnson's Baby Shampoo Wheatgerm ","200ml");
            registerProduct("3574660149807","Clean & Clear Scrub Daily Blackhead Clearing ","150ml");
            registerProduct("3574660333800","Clean & Clear Make-Up Remover Deep Cleansing ","150ml");
            registerProduct("3574660420913","Johnson's Baby Lotion Mildness ","200ml");
            registerProduct("3574660420951","Johnson's Baby Lotion Mildness ","500ml");
            registerProduct("3574660500714","Johnson's Baby Shampoo No more Tangles ","200ml");
            registerProduct("3574660583168","Johnson's Facial Wash Daily Essentials ","150ml");
            registerProduct("3574661053837","Clean & Clear Blackhead Clearing Bar ","75g");
            registerProduct("3600541124066","Garnier Color Naturals Hair Color Kit# 4.15 Box"," ");
            registerProduct("3600541124110","Garnier Color Naturals Hair Color Kit# 5.25 Box"," ");
            registerProduct("3600541125254","Garnier Color Naturals Hair Color Kit# 1+ Box"," ");
            registerProduct("3610340013010","Garnier Face Wash Purifying Neem ","100ml");
            registerProduct("3610340013058","Garnier Face Wash Purifying Neem ","50g");
            registerProduct("3610340018022","Garnier Color Naturals Hair Color Kit# 1.17 Box"," ");
            registerProduct("3610340025464","Garnier Color Naturals Hair Color Kit# 4.7 Box"," ");
            registerProduct("3610340025549","Garnier Color Naturals Hair Color Kit# 7.7 Box"," ");
            registerProduct("3800020456293","Kit Kat Chocolate Chunky ","40g");
            registerProduct("40000005179","M&M's Chocolate Beans Milk ","45g");
            registerProduct("40000488064","M&M's Chocolate Beans Chocolate Pouch ","180g");
            registerProduct("4005500049623","Kit Kat Chocolate Chunky Mini ","250g");
            registerProduct("40057767","Kit Kat Chocolate Dark ","45g");
            registerProduct("40099118","Wrigley's Chewing Gum Spearmint ","15g");
            registerProduct("40099217","Wrigley's Chewing Gum Double Mint (Pack of 5)"," ");
            registerProduct("40099316","Wrigley's Chewing Gum Juicy Fruit 5's"," ");
            registerProduct("40099361","Extra Chewing Gum Peppermint  (Pack of 30)","14g");
            registerProduct("401019","Roomi Air Freshner Block"," ");
            registerProduct("4011100023710","Mars Chocolate Miniatures ","150g");
            registerProduct("4011100023925","Bounty Chocolate Miniatures ","150g");
            registerProduct("4011100037915","Snickers Chocolate Miniatures ","150g");
            registerProduct("4011100156364","Skittles Fruit Bite ","38g");
            registerProduct("4015400205227","Always Pads Ultra Thin Duo (Pack of 16)"," ");
            registerProduct("4015400205289","Always Pads Ultra Thin Large Value Pack (Pack of 14)"," ");
            registerProduct("4015400205319","Always Pads Ultra Night (Pack of 6)"," ");
            registerProduct("4015400214496","Always Pads Ultra Normal (Pack of 8)"," ");
            registerProduct("4015400256175","Pampers Active Boy Pants Junior (Pack of 34)"," ");
            registerProduct("4015400256205","Pampers Active Girl Pants Junior (Pack of 34)"," ");
            registerProduct("4015400256304","Pampers Active Boy Pants XL (Pack of 33)"," ");
            registerProduct("4015400256335","Pampers Active Girl Pants XL (Pack of 33)"," ");
            registerProduct("4015400416869","Always Pads Super Plus (Pack of 18)"," ");
            registerProduct("4015400417491","Always Pads Maxi Thick long (Pack of 8)"," ");
            registerProduct("4015400457848","Always Pads Maxi Super Plus (Pack of 9)"," ");
            registerProduct("4015400457879","Always Pads Maxi Thick (Pack of 16)"," ");
            registerProduct("4015400566120","Always Diamond Pads Ultra Thin (Pack of 8)"," ");
            registerProduct("4015400566151","Always Diamond Pads Long (Pack of 16)"," ");
            registerProduct("4015400566182","Always Diamond Pads Extra Long (Pack of 7)"," ");
            registerProduct("4015400566212","Always Diamond Pads Extra Long (Pack of 14)"," ");
            registerProduct("4015400641841","Pampers Diapers Premium Care Mini (Pack of 40)"," ");
            registerProduct("4015400641902","Pampers Diapers Premium Care Maxi (Pack of 32)"," ");
            registerProduct("4015400641964","Pampers Diapers Premium Mega Mini (Pack of 80)"," ");
            registerProduct("4015400641995","Pampers Diapers Premium Mega Midi (Pack of 72)"," ");
            registerProduct("4015400642022","Pampers Diapers Premium Mega Maxi (Pack of 64)"," ");
            registerProduct("4015400642053","Pampers Diapers Premium Mega Junior (Pack of 64)"," ");
            registerProduct("4015400646860","Always Pads Classic Thick With Wings (Pack of 9)"," ");
            registerProduct("4015400680635","Pampers Diapers Mini (Pack of 20)"," ");
            registerProduct("4015400680666","Pampers Diapers Midi Value Pack (Pack of 18)"," ");
            registerProduct("4015400680758","Pampers Diapers Mini (Pack of 40)"," ");
            registerProduct("4015400680789","Pampers Diapers Medium (Pack of 36)"," ");
            registerProduct("4015400680819","Pampers Diapers Large (pack of 32)"," ");
            registerProduct("4015400680840","Pampers Diapers Jumbo Junior (Pack of 32)"," ");
            registerProduct("4015400680932","Pampers Diapers Mega Pack Maxi (Pack of 64)"," ");
            registerProduct("4015400680963","Pampers Diapers Mega Pack Junior (Pack of 64)"," ");
            registerProduct("4015400680994","Pampers Diapers Mega Pack Mini (Pack of 80)"," ");
            registerProduct("4015400681021","Pampers Diapers Mega Pack Midi (Pack of 72)"," ");
            registerProduct("4015600379599","Head & Shoulders Shampoo Itchy Scalp Care ","200ml");
            registerProduct("4015600379681","Head & Shoulders Shampoo Itchy Scalp Care ","400ml");
            registerProduct("4015600834982","Pantene Shampoo Moisture Renewal ","200ml");
            registerProduct("4015600835149","Pantene Shampoo Moisture Renewal ","400ml");
            registerProduct("4084500201248","Safeguard Soap Pure White ","150g");
            registerProduct("4084500201279","Safeguard Soap Lemon ","150g");
            registerProduct("4084500201347","Safeguard Soap Floral Scent ","150g");
            registerProduct("418033","Roomi Toilet Blocks Blue"," ");
            registerProduct("4201010100261","Shield Feeding Bottles Evenflo Plus ","200ml");
            registerProduct("4201010100278","Shield Feeding Bottles Evenflo Plus ","300ml");
            registerProduct("4204010100064","Shield Teether Silicone"," ");
            registerProduct("42112907","Extra Chewing Gum Strawberry"," ");
            registerProduct("42113188","Extra Chewing Gum Professional  White","14g");
            registerProduct("42124368","Extra Chewing Gum Professional (White)  (Pack of 14)","12g");
            registerProduct("42247340","Extra Chewing Gum Bubblemint (Pack of 30)"," ");
            registerProduct("4607065371340","Bounty Chocolate ","57g");
            registerProduct("46123367","Skittles Fruit Bite ","10g");
            registerProduct("4710032501838","Clean & Clear Cleanser Daily Pore ","100g");
            registerProduct("4710032503078","Johnson's Baby Wipes Skin Care (Pack of 20)"," ");
            registerProduct("4710032503634","Clean & Clear Cleanser Deep Action Oil Free ","100ml");
            registerProduct("4800361381260","Milo Drinking Powder Pouch ","600g");
            registerProduct("4800361381284","Milo Drinking Powder Pouch ","1kg");
            registerProduct("4801010104209","Johnson's Baby Powder Nourishing ","100g");
            registerProduct("4801010105206","Johnson's Baby Powder Pink Blossoms ","100g");
            registerProduct("4801010107200","Johnson's Baby Powder Cooling ","100g");
            registerProduct("4801010107309","Johnson's Baby Cooling Powder ","200g");
            registerProduct("4801010121312","Johnson's Baby Cologne Regular ","125ml");
            registerProduct("4801010125303","Johnson's Baby Cologne Powder Mist ","125ml");
            registerProduct("4801010127314","Johnson's Baby Cologne Heaven ","125ml");
            registerProduct("4801010501213","Johnson's Baby Oil ","50ml");
            registerProduct("4801010560500","Johnson's Soap  White","100g");
            registerProduct("4801010561309","Johnson's Soap Blossoms ","100g");
            registerProduct("4801010562108","Johnson's Soap Milk ","100g");
            registerProduct("4902705111998","Meiji Powder Milk Pre ","400g");
            registerProduct("4902705112551","Meiji Powder Milk Lactoless ","350g");
            registerProduct("4902705114654","Meiji Powder Milk FM-T ","400g");
            registerProduct("4902705114722","Meiji Powder Milk FM-T ","900g");
            registerProduct("4902705114739","Meiji Powder Milk FU ","400g");
            registerProduct("4902705114746","Meiji Powder Milk FU ","900g");
            registerProduct("4902705119048","Meiji Powder Milk Mamilac ","350g");
            registerProduct("4902720087513","Morinaga Powder Milk BF-1 ","900g");
            registerProduct("4902720087681","Morinaga Powder Milk BF-1 ","400g");
            registerProduct("4902720106122","Morinaga Powder Milk BF-Mama Vanilla ","200g");
            registerProduct("4902720106139","Morinaga Powder Milk BF-Mama Chocolate ","200g");
            registerProduct("5000127012097","Kellogg's Corn Flakes ","500g");
            registerProduct("5000127014084","Kellogg's Corn Flakes ","1kg");
            registerProduct("5000127141193","Kellogg's All-Bran ","500g");
            registerProduct("5000127163195","Kellogg's Special K ","500g");
            registerProduct("5000127421257","Kellogg's Cereal Just Right ","500g");
            registerProduct("5000159301022","Minis Chocolate Best ","500g");
            registerProduct("5000159404259","Snickers Chocolate  (Pack of 6)","54g");
            registerProduct("5000159407236","Mars Chocolate ","51g");
            registerProduct("5000159434539","Bounty Chocolate 5x","57g");
            registerProduct("5000159459228","Twix Chocolate ","50g");
            registerProduct("5000159459273","Twix Chocolate ","25g");
            registerProduct("5000159461122","Snickers Chocolate ","54g");
            registerProduct("5000159462594","Twix Chocolate  (Pack of 5)","58g");
            registerProduct("5000189363069","After Eight Chocolate Mint ","200g");
            registerProduct("5000189983281","After Eight Chocolate Mint ","400g");
            registerProduct("5000207005506","Johnson's Baby Wipes Skin Care (Pack of 80)"," ");
            registerProduct("5000207007425","Clean & Clear Face Wash Daily Exfoliating ","150ml");
            registerProduct("5011321359819","Head & Shoulders Shampoo (2 in 1) Lively & Silky ","200ml");
            registerProduct("5011321360709","Head & Shoulders Shampoo Silky Black ","200ml");
            registerProduct("5011321361195","Head & Shoulders Shampoo Silky Black ","400ml");
            registerProduct("5011321390676","Head & Shoulders Shampoo Classic Clean ","200ml");
            registerProduct("5011321391284","Head & Shoulders Shampoo Classic Clean ","400ml");
            registerProduct("5011321525924","Head & Shoulders Shampoo Hair Fall Defense For Her ","200ml");
            registerProduct("50127030","Kellogg's Bran Flakes Sultana ","500g");
            registerProduct("5012712003793","Enfamil Powder Milk O-Lac Lactose-Free ","400g");
            registerProduct("50127504","Kellogg's Fruit'n Fibre ","500g");
            registerProduct("5013965640476","Head & Shoulders Shampoo Moisturizing Scalp Care ","200ml");
            registerProduct("5013965705120","Safeguard Soap Aloe Vera ","115g");
            registerProduct("5013965705182","Safeguard Soap Pure White ","115g");
            registerProduct("5013965919251","Head & Shoulders Shampoo Moisturizing Scalp Care ","400ml");
            registerProduct("50173686","Extra Chewing Gum Spearmint ","14g");
            registerProduct("5028217000991","Laziza Kheer Mix Standard ","155g");
            registerProduct("5028217001028","Laziza Firni Khas Saffron ","150g");
            registerProduct("5028217001035","Laziza Firni Khas Kewra ","150g");
            registerProduct("5028217001042","Laziza Kheer Mix Almond & Saffron ","155g");
            registerProduct("5028217001059","Laziza Kheer Mix Pista & Coconut ","155g");
            registerProduct("5028217001066","Laziza Gulab Jamun Mix ","85g");
            registerProduct("5028217001080","Laziza Kheer Mix Gajar ","145g");
            registerProduct("5028217001257","Laziza Sheer Khurma MIx Saffron ","160g");
            registerProduct("5028217001486","Laziza Shahi Tukra Dessert Mix ","180g");
            registerProduct("5028217001950","Laziza Kheer Mix Dry Fruit ","160g");
            registerProduct("5028217002322","Laziza Ras Malai Mix Almond ","75g");
            registerProduct("5028217002353","Laziza Kheer Mix Standard Economy Pack ","310g");
            registerProduct("5028217002742","Laziza Kheer Mix Vermacelli ","155g");
            registerProduct("5028217002759","Laziza Kheer Mix Kajoo ","155g");
            registerProduct("5028217004654","Laziza Faluda Mix Jelly ","235g");
            registerProduct("5028217004661","Laziza Faluda Mix Rabri ","200g");
            registerProduct("5028217005606","Laziza Faluda Mix Jelly Strawberry ","195g");
            registerProduct("5028217006252","Laziza Doodh Dullari Dessert Mix ","225g");
            registerProduct("5028217999967","Laziza Ras Malai Mix Pistachio ","75g");
            registerProduct("5028217999974","Laziza Ras Malai Mix Standard ","75g");
            registerProduct("5078643001086","Shezan Pickle Kasundi Mango ","325g");
            registerProduct("5078643001116","Shezan Pickle Garlic ","325g");
            registerProduct("5078643001123","Shezan Pickle Hyderabadi Mix ","325g");
            registerProduct("5078643001208","Shezan Jam Mango ","440g");
            registerProduct("5078643001215","Shezan Jam Mix Fruit ","440g");
            registerProduct("5078643001222","Shezan Jam Apple ","440g");
            registerProduct("5078643001246","Shezan Jam Strawberry ","440g");
            registerProduct("5078643001260","Shezan Jelly Apple ","440g");
            registerProduct("5078643001284","Shezan Jam Sweet Orange ","440g");
            registerProduct("5078643001369","Shezan Pickle Satrang ","350g");
            registerProduct("5078643001406","Shezan Pickle Chilli In Oil ","300g");
            registerProduct("5078643001529","Shezan Jam Pineapple ","440g");
            registerProduct("5110000300323","Shield Blessing Feeding Bottle ","60ml");
            registerProduct("5110000300354","Shield Blessing Feeding Bottles ","275ml");
            registerProduct("5110000300361","Shield Blessing Feeding Bottle ","130ml");
            registerProduct("5110000300538","Shield Feeding Bottle Evenflo ","125ml");
            registerProduct("5110000300545","Shield Feeding Bottles Evenflo ","250ml");
            registerProduct("5110000300552","Shield Feeding Bottles Evenflo ","60ml");
            registerProduct("5110000300569","Shield Feeder Bottle With Handle Evenflo ","125ml");
            registerProduct("5110000300576","Shield Feeder Bottle With Handle Evenflo ","250ml");
            registerProduct("5110000300644","Shield Feeding Bottles Zoo ","125ml");
            registerProduct("5110000300651","Shield Feeding Bottles Zoo ","250ml");
            registerProduct("5110000310032","Shield Training Cup"," ");
            registerProduct("5110000400092","Shield Silicone Nipple Evenflo Regular 2's"," ");
            registerProduct("5110000400115","Shield Nipple Evenflo Extra Soft 2's"," ");
            registerProduct("5410076652228","Pantene Shampoo Nature Fusion  Local","180ml");
            registerProduct("5410076652266","Pantene Shampoo Nature Fusion  Local","360ml");
            registerProduct("5410076789252","Safeguard Soap Floral Scent ","115g");
            registerProduct("5410076881994","Pantene Shampoo Sheer Volume ","200ml");
            registerProduct("5410076896516","Head & Shoulders Conditioner Classic Clean ","360ml");
            registerProduct("5410076896639","Head & Shoulders Conditioner Itchy Scalp Care ","360ml");
            registerProduct("5410076937448","Pantene Shampoo Smooth & Strong ","200ml");
            registerProduct("5410076937479","Pantene Shampoo Smooth & Strong ","400ml");
            registerProduct("5410076937530","Pantene Shampoo Anti Hair Fall ","200ml");
            registerProduct("5410076937691","Pantene Shampoo Milky Extra Treatment ","200ml");
            registerProduct("5410076937738","Pantene Shampoo Milky Extra Treatment ","400ml");
            registerProduct("5410076937769","Pantene Shampoo Deep Black ","200ml");
            registerProduct("5410076937790","Pantene Shampoo Deep Black ","400ml");
            registerProduct("5410076937820","Pantene Shampoo Anti Dandruf ","200ml");
            registerProduct("5410076966677","Pantene Conditioner Smooth & Strong ","200ml");
            registerProduct("5410076966806","Pantene Conditioner Anti Hair Fall ","200ml");
            registerProduct("5410076966868","Pantene Conditioner Milky Extra Treatment ","200ml");
            registerProduct("5449000000712","Fanta Drink Can ","250ml");
            registerProduct("5449000000996","Coca Cola Drink Can ","330ml");
            registerProduct("5449000008046","Coca Cola Drink Can ","250ml");
            registerProduct("5449000014535","Sprite Drink Can ","330ml");
            registerProduct("5709381117011","Guard Rice Supereme Basmati ","1kg");
            registerProduct("5733369976796","Guard Rice Super Kernel Basmati ","1kg");
            registerProduct("5733369995759","Guard Rice Super Kernel Basmati ","5kg");
            registerProduct("5769745823316","Guard Rice Tukra Basmati ","1kg");
            registerProduct("5769745823354","Guard Rice Tukra Basmati ","5kg");
            registerProduct("5796321456002","Guard Rice Ultimate Basmati ","1kg");
            registerProduct("5796321475003","Guard Rice Ultimate Basmati ","5kg");
            registerProduct("5809985226437","Guard Rice Long Grain ","1kg");
            registerProduct("5809985229520","Guard Rice Long Grain ","5kg");
            registerProduct("5900951251849","Twix Chocolate Top ","21g");
            registerProduct("6001011321126","Johnson's Baby Jelly Unscented ","250ml");
            registerProduct("6001011321133","Johnson's Baby Jelly Scented ","250ml");
            registerProduct("6001106114626","Harpic Toilet Cleaner Power ","500ml");
            registerProduct("6001106114633","Harpic Toilet Cleaner Power ","750ml");
            registerProduct("6001106114640","Harpic Toilet Cleaner Fresh ","500ml");
            registerProduct("6001106208400","Harpic Toilet Cleaner Rose ","500ml");
            registerProduct("6001106208417","Harpic Toilet Cleaner Rose ","750ml");
            registerProduct("6001106208554","Robin Liquid Neel ","300ml");
            registerProduct("6001106209025","Dettol Multi Purpose Cleaner Pine tr","1l");
            registerProduct("6001106209032","Dettol Multi Purpose Cleaner Floral tr","1l");
            registerProduct("6001106209261","Dettol Soap Original ","100g");
            registerProduct("6001106209285","Dettol Soap Skin Care ","100g");
            registerProduct("6001106209308","Dettol Soap Cool ","100g");
            registerProduct("6001106209506","Dettol Soap Original ","145g");
            registerProduct("6001106209513","Dettol Soap Skin Care ","145g");
            registerProduct("6001106209520","Dettol Soap Cool ","145g");
            registerProduct("6001106209551","Dettol Soap Re-Energize ","100g");
            registerProduct("6001106209612","Dettol Soap Re-Energize ","145g");
            registerProduct("6001106209643","Veet Hair Removing Lotion Normal Skin ","80g");
            registerProduct("6001106209650","Veet Hair Removing Lotion Normal Skin ","40g");
            registerProduct("6001106209797","Veet Hair Removing Lotion Dry Skin ","80g");
            registerProduct("6001106209803","Veet Hair Removing Lotion Dry Skin ","40g");
            registerProduct("6001106210038","Dettol Soap Radiance ","100g");
            registerProduct("6001106210045","Dettol Soap Radiance ","145g");
            registerProduct("6001106309404","Dettol Multi Purpose Cleaner Floral ","200ml");
            registerProduct("6001106309497","Veet Hair Removing Cream Dry Skin ","25g");
            registerProduct("6001106309503","Veet Hair Removing Cream Dry Skin ","50g");
            registerProduct("6001106309510","Veet Hair Removing Cream Sensitive Skin ","25g");
            registerProduct("6001106309527","Veet Hair Removing Cream Sensitive Skin ","50g");
            registerProduct("6001106309534","Veet Hair Removing Cream Normal Skin ","25g");
            registerProduct("6001106309541","Veet Hair Removing Cream Normal Skin ","50g");
            registerProduct("6001106309565","Dettol Multi Purpose Cleaner Floral ","500ml");
            registerProduct("6001106309572","Robin Powder Neel ","225g");
            registerProduct("6001106309602","Harpic Toilet Cleaner Fresh ","750ml");
            registerProduct("6001106309725","Robin Liquid Neel ","150ml");
            registerProduct("6001106309831","Robin Bleach tr","1l");
            registerProduct("6001106309985","Robin Bleach ","500ml");
            registerProduct("6003770000670","Nando's Sauce Extra Hot Peri-Peri ","250ml");
            registerProduct("6003770002407","Nando's Sauce Lemon & Herb Peri-Peri ","125 ml");
            registerProduct("6003770002414","Nando's Sauce Lemon & Herb Peri-Peri ","250ml");
            registerProduct("60052360","Johnson's Baby Jelly Unscented ","100ml");
            registerProduct("60052377","Johnson's Baby Jelly Scented ","100ml");
            registerProduct("6011062085922","Veet Hair Removing Cream Dry Skin ","100g");
            registerProduct("6011062086080","Veet Hair Removing Cream Normal Skin ","100g");
            registerProduct("6011062086158","Veet Hair Removing Cream Sensitive Skin ","100ml");
            registerProduct("621501001091","Hub Pak Salt Iodized ","800g");
            registerProduct("621501001107","Hub Pak Salt Refined ","800g");
            registerProduct("6221033101210","Heinz Tomato Ketchup  BTL","200g");
            registerProduct("6221033101289","Heinz Tomato Ketchup Pouch ","300g");
            registerProduct("6221033101517","Heinz Tomato Ketchup ","513g");
            registerProduct("6221033101814","Heinz Tomato Ketchup  BTL","850g");
            registerProduct("6221033111233","Heinz Tomato Paste  BTL","200g");
            registerProduct("6221033171015","Heinz Sauce Hot ","88g");
            registerProduct("6221033175518","Heinz Vinegar Natural ","500ml");
            registerProduct("6221033175914","Heinz Vinegar Natural tr","1l");
            registerProduct("6221134000108","Galaxy Chocolate Flutes 22.","5g");
            registerProduct("6221134000986","Galaxy Chocolate Hazelnut ","40g");
            registerProduct("6221134001129","Galaxy Chocolate Fruit & Nut ","40g");
            registerProduct("6281034900175","Rani Juice Peach Can ","240ml");
            registerProduct("6281073210143","Alshifa Honey ","250g");
            registerProduct("6281073210181","Alshifa Honey ","500g");
            registerProduct("6281073210228","Alshifa Honey ","1kg");
            registerProduct("6281073210266","Alshifa Honey ","125g");
            registerProduct("6281073210341","Alshifa Honey ","750g");
            registerProduct("6281073210426","Alshifa Honey Acacia ","500g");
            registerProduct("6281073210501","Alshifa Honey Orange ","500g");
            registerProduct("6281073210662","Alshifa Honey Lime Tree ","500g");
            registerProduct("6281100870258","Glade Air Freshner Rose ","300ml");
            registerProduct("6281100870296","Glade Air Freshner Lavender ","300ml");
            registerProduct("6281100870975","Glade Air Freshner Refreshing Raspberry ","300ml");
            registerProduct("6281100875536","Glade Air Freshner Morning Freshness ","300ml");
            registerProduct("6281100875550","Glade Air Freshner I Love You ","300ml");
            registerProduct("6281100879695","Glade Air Freshner Ocean Escape ","300ml");
            registerProduct("6290090014559","Heinz Tomato Ketchup ","300g");
            registerProduct("6290090014962","Complan Glucon-D Plus Drinking Powder Original  Box","325g");
            registerProduct("6290090014979","Complan Glucon-D Plus Drinking Powder Taaza Limu  Box","300g");
            registerProduct("6290090024107","Complan Drinking Powder Vanilla  Box","200g");
            registerProduct("6290090024626","Complan Drinking Powder Mango ","200g");
            registerProduct("6290090024718","Heinz Sauce Chilli Garlic ","340g");
            registerProduct("6290090033239","Heinz Tomato Ketchup 1.","07Kg");
            registerProduct("6290090043108","Complan Drinking Powder Chocolate Box ","200g");
            registerProduct("6290090043139","Complan Drinking Powder Chocolate  Jar","400g");
            registerProduct("6290090043146","Complan Drinking Powder Vanilla  Jar","400g");
            registerProduct("6290090043207","Complan Drinking Powder Strawberry ","200g");
            registerProduct("6290090043214","Complan Drinking Powder Strawberry  Jar","400g");
            registerProduct("6294001813392","Mars Chocolate ","32g");
            registerProduct("6294001813415","Mars Chocolate  (Pack of 6)","51g");
            registerProduct("6294001813668","Galaxy Chocolate Jewels Jar ","650g");
            registerProduct("6294001814139","Galaxy Chocolate Jewels Jar ","400g");
            registerProduct("6294001814153","Galaxy Chocolate Jewels Jar ","200g");
            registerProduct("6294001820338","Galaxy Chocolate Minis Hazelnut Bag ","150g");
            registerProduct("6294001820628","Twix Chocolate Mini ","300g");
            registerProduct("6294003532987","Kit Kat Chocolate 4 Finger 41.","5g");
            registerProduct("6294003557560","Kit Kat Chocolate 2 Finger 12x20.","5g");
            registerProduct("6294003557645","Kit Kat Chocolate 4 Finger 6x41.","5g");
            registerProduct("6295120019900","Veet Body Wax Strips Dry Skin (Pack of 12)"," ");
            registerProduct("6295120019917","Veet Body Wax Strips Sensitive Skin (Pack of 12)"," ");
            registerProduct("6295120023112","Dettol Soap Lasting Fresh ","100g");
            registerProduct("6295120023136","Dettol Soap Lasting Fresh ","145g");
            registerProduct("6315836020240","Guard Rice Supereme Basmati ","5kg");
            registerProduct("6529880216126","Guard Rice Tibar Basmati ","5kg");
            registerProduct("6529880216287","Guard Rice Tibar Basmati ","1kg");
            registerProduct("671866116565","Kolson Slanty Blue ","22g");
            registerProduct("671866116572","Kolson Slanty Green ","16g");
            registerProduct("671866116589","Kolson Slanty Red ","16g");
            registerProduct("671866116800","Kolson Potato Sticks ","23g");
            registerProduct("671866116817","Kolson Potato Sticks ","50g");
            registerProduct("671866116909","Kolson Twich ","19g");
            registerProduct("671866117364","Kolson Snackers Pizza ","18g");
            registerProduct("671866117371","Kolson Snackers Hot Masala ","18g");
            registerProduct("671866117388","Kolson Snackers French Cheese ","18g");
            registerProduct("671866117395","Kolson Snackers Salt & Pepper ","18g");
            registerProduct("671866149754","Kolson Spaghetti Box ","450g");
            registerProduct("671866149761","Kolson Spaghetti Fancy ","500g");
            registerProduct("671866149846","Kolson Macaroni Long Box ","450g");
            registerProduct("671866149907","Kolson Macaroni Elbow Bag (Large) ","400g");
            registerProduct("671866149914","Kolson Macaroni Spirals Bag ","400g");
            registerProduct("671866149921","Kolson Macaroni Elbow Ridged Bag ","400g");
            registerProduct("671866149969","Kolson Macaroni Elbow Twisted Bag (Medium) ","400g");
            registerProduct("671866149976","Kolson Macaroni Penne Bag ","400g");
            registerProduct("671866150064","Kolson Vermicelli Dhanak ","200g");
            registerProduct("671866150095","Kolson Macaroni Shell Bag (Large) ","400g");
            registerProduct("671866150132","Kolson Egg Noodles Chinese ","227g");
            registerProduct("671866150149","Kolson Lasagne ","400g");
            registerProduct("689076783477","King Air Freshner Red Rose ","300ml");
            registerProduct("689076783675","King Air Freshner Lavender ","300ml");
            registerProduct("689076783774","King Air Freshner Sandal Wood ","300ml");
            registerProduct("705105554730","Kingtox Air Freshner Refill Jasmine Push & Fresh"," ");
            registerProduct("705105554938","Kingtox Air Freshner Refill Lavender Push & Fresh ","14ml");
            registerProduct("705105558035","Kingtox Air Freshner Kit Jasmine Push & Fresh ","14ml");
            registerProduct("705105558134","Kingtox Air Freshner Kit Lavender Push & Fresh"," ");
            registerProduct("705105558332","Kingtox Air Freshner Kit Pioson Push & Fresh"," ");
            registerProduct("7137571774368","King Toilet Cleaner Sweepy ","600ml");
            registerProduct("7181221034166","King Glass & Household Cleaner Anti Static Klear ","500ml");
            registerProduct("718122103614","King Chamak Blue ","100ml");
            registerProduct("718122104116","King Phenyle Sweepy 2.tr","75l");
            registerProduct("7184123113915","Guard Rice Awami Basmati ","1kg");
            registerProduct("7184123113953","Guard Rice Awami Basmati ","5kg");
            registerProduct("7184191119017","Guard Rice Super Kernel Sella ","1kg");
            registerProduct("7184191119055","Guard Rice Super Kernel Sella ","5kg");
            registerProduct("72121","Ice Cream Spoon Set Cocktail"," ");
            registerProduct("72122","Tea Spoon Set Cocktail (4 Pieces)"," ");
            registerProduct("72123","Tea Spoon Set Cocktail (4 Pieces)"," ");
            registerProduct("72130","Cake Fork Cocktail (4 Pieces)"," ");
            registerProduct("7322441209990","Johnson's Baby Bath Time Gift Pack"," ");
            registerProduct("7501056349288","Dove Beauty Soap Pink Rosa ","113g");
            registerProduct("7612100060865","Ovaltine Drinking Powder Box ","200g");
            registerProduct("7613031513093","Nesquik Drinking Powder Banana ","300g");
            registerProduct("7613032991852","Nestle Resource Diabetes ","400g");
            registerProduct("7613032992019","Nestle Resource Optimum ","400g");
            registerProduct("7616100145553","Nescafe Coffee Gold ","100g");
            registerProduct("7616100759330","Nido Powder Milk 1+ Tin 1.","8kg");
            registerProduct("7649453855312","Guard Rice Brown Basmati Low Fat Jar 1.","5kg");
            registerProduct("7702018037896","Gillette Mach3 Cartridges Sensitive (Pack of 4)"," ");
            registerProduct("7702018877591","Gillette Cartridges Fusion (Pack of 4)"," ");
            registerProduct("788821000866","Shan Chutney Mango ","400g");
            registerProduct("788821001252","Shan Recipes Bombay Biryani","120g");
            registerProduct("788821001269","Shan Recipes Nihari ","120g");
            registerProduct("788821001276","Shan Recipes Biryani","100g");
            registerProduct("788821001283","Shan Recipes Karahi/Fry Gosht ","100g");
            registerProduct("788821001290","Shan Recipes Korma ","100g");
            registerProduct("788821001306","Shan Recipes Achar Gosht ","100g");
            registerProduct("788821001313","Shan Recipes Chicken Tikka ","100g");
            registerProduct("788821001320","Shan Recipes Fish ","100g");
            registerProduct("788821001337","Shan Recipes Chicken ","100g");
            registerProduct("788821001436","Shan Recipes Chinese Chicken Vegetables ","40g");
            registerProduct("788821001481","Shan Recipes Malaysian Chicken Wings ","40g");
            registerProduct("788821001658","Shan Recipes Chinese Chop Suey ","40g");
            registerProduct("788821002082","Shan Recipes Pilau Biryani ","100g");
            registerProduct("788821002099","Shan Recipes Sindhi Biryani ","120g");
            registerProduct("788821002105","Shan Recipes Paya ","100g");
            registerProduct("788821002112","Shan Recipes Tikka Seekh Kabab ","100g");
            registerProduct("788821002594","Shan Pickle Mix Pouch ","500g");
            registerProduct("788821003430","Shan Chutney Tomato ","315g");
            registerProduct("788821003461","Shan Chutney Green ","315g");
            registerProduct("788821003478","Shan Chutney Tamarind","315g");
            registerProduct("788821003607","Shan Pickle Carrot ","300g");
            registerProduct("788821004352","Shan Recipe Paste Achar Gosht ","310g");
            registerProduct("788821004406","Shan Recipe Paste Korma ","310g");
            registerProduct("788821004413","Shan Recipe Paste Pilau Biryani ","310g");
            registerProduct("788821005458","Shan Recipes Haleem Double ","100g");
            registerProduct("788821005465","Shan Recipes Tandori Masala ","100g");
            registerProduct("788821005472","Shan Recipes Shami Kabab ","100g");
            registerProduct("788821010025","Shan Pickle Mix Jar ","1kg");
            registerProduct("788821022028","Shan Pickle Mango Jar ","1kg");
            registerProduct("788821062192","Shan Spices Tez Lal Mirch Powder ","100g");
            registerProduct("788821062215","Shan Spices Tez Lal Mirch Powder ","400g");
            registerProduct("788821104021","Shan Pickle Hyderabadi Mix Jar ","1kg");
            registerProduct("788821122056","Shan Pickle Mango ","300g");
            registerProduct("788821123053","Shan Pickle Chilli ","300g");
            registerProduct("788821150127","Shan Recipes Biryani ","50g");
            registerProduct("788821150134","Shan Recipes Punjabi Yakhni Pilau ","50g");
            registerProduct("788821150141","Shan Recipes Pilau Biryani ","50g");
            registerProduct("788821150189","Shan Recipes Bombay Biryani","65g");
            registerProduct("788821150196","Shan Receipes fish Biryani ","50g");
            registerProduct("788821150202","Shan Recipes Memon Mutton Biryani ","60g");
            registerProduct("788821150219","Shan Recipes Sindhi Biryani ","65g");
            registerProduct("788821150257","Shan Recipes Karachi Beef Biryani ","60g");
            registerProduct("788821150264","Shan Recipes Malay Chicken Biryani ","60g");
            registerProduct("788821150363","Shan Recipes Achar Gosht ","50g");
            registerProduct("788821150370","Shan Recipes Chicken ","50g");
            registerProduct("788821150387","Shan Recipes Kofta ","50g");
            registerProduct("788821150394","Shan Recipes Korma ","50g");
            registerProduct("788821150417","Shan Recipes Murgh Cholay ","50g");
            registerProduct("788821150592","Shan Recipes Meat & Vegetable ","100g");
            registerProduct("788821150684","Shan Recipes Nihari ","60g");
            registerProduct("788821150790","Shan Recipes Karahi/Fry Gosht ","50g");
            registerProduct("788821150820","Shan Recipes Keema ","50g");
            registerProduct("788821150837","Shan Recipes Chappli Kabab ","100g");
            registerProduct("788821150905","Shan Recipes Fish ","50g");
            registerProduct("788821150929","Shan Recipes Shami Kabab ","50g");
            registerProduct("788821150936","Shan Recipes Haleem ","60g");
            registerProduct("788821150943","Shan Recipes Lahori Charga ","50g");
            registerProduct("788821151148","Shan Recipes Fruit Chaat ","50g");
            registerProduct("788821151247","Shan Recipes Tandori Masala ","50g");
            registerProduct("788821151254","Shan Recipes Bihari Kabab ","50g");
            registerProduct("788821151261","Shan Recipes Chicken Tikka ","50g");
            registerProduct("788821151278","Shan Recipes Seekh Kabab ","50g");
            registerProduct("788821151292","Shan Recipes Tikka Boti ","50g");
            registerProduct("788821151308","Shan Recipes Tikka Seekh Kabab ","50g");
            registerProduct("788821151421","Shan Recipes Chicken Jalfrezi ","50g");
            registerProduct("788821151438","Shan Recipes Chicken Handi ","50g");
            registerProduct("788821151452","Shan Recipes Chicken White Korma ","40g");
            registerProduct("788821151629","Shan Recipes Dal ","100g");
            registerProduct("788821151636","Shan Recipes Vegetable ","100g");
            registerProduct("788821151681","Shan Recipes Special Dahi Bara ","150g");
            registerProduct("788821151704","Shan Recipes Easy Cook Haleem ","300g");
            registerProduct("788821151711","Shan Recipes Special Shahi Haleem ","300g");
            registerProduct("788821151810","Shan Spices Qasuri Methi Leaves ","25g");
            registerProduct("788821152602","Shan Spices Taaza Dhania Powder ","100g");
            registerProduct("788821152619","Shan Spices Taaza Dhania Powder ","200g");
            registerProduct("788821152626","Shan Spices Khalis Haldee Powder ","50g");
            registerProduct("788821152657","Shan Spices Zafrani Garam Masala ","50g");
            registerProduct("788821152701","Shan Khatai Powder ","50g");
            registerProduct("788821153005","Shan Spices Tez Lal Mirch Powder ","200g");
            registerProduct("788821153098","Shan Spices Khushbudar Lehsan Powder ","50g");
            registerProduct("788821153104","Shan Spices Taaza Dhania Powder ","400g");
            registerProduct("7891000002032","Nescafe Coffee Matinal ","50g");
            registerProduct("82325","Wine Glass Amper Cup Light Brown"," ");
            registerProduct("82326","Wine Glass Purple"," ");
            registerProduct("82329","Wine Glass Clear"," ");
            registerProduct("83199","Double Bed Sheet Printed"," ");
            registerProduct("83332","MireIla Vase Spring Ceramic (Large)"," ");
            registerProduct("83334","Mirella Vase Spring Ceramic (Small)"," ");
            registerProduct("83349","Candle Holder Bamboo Slat Striped with Stand"," ");
            registerProduct("83400","Ceramic Handle Vase"," ");
            registerProduct("8410179006321","Borges Olive Oil Pomace Tin ","100ml");
            registerProduct("8410179099972","Borges Olive Oil Extra Virgin tr","2 L");
            registerProduct("8410179100012","Borges Olive Oil Extra Virgin ","1000ml");
            registerProduct("8410179100036","Borges Olive Oil Extra Virgin ","500ml");
            registerProduct("8410179100067","Borges Olive Oil Extra Virgin ","125ml");
            registerProduct("8410179300757","Borges Olive Oil Extra Light tr","2L");
            registerProduct("8410179300801","Borges Olive Oil Extra Light Glass ","1000ml");
            registerProduct("8410179300818","Borges Olive Oil Extra Light ","750ml");
            registerProduct("8410179300825","Borges Olive Oil Extra Light ","500ml");
            registerProduct("8410179300863","Borges Olive Oil Extra Light ","125ml");
            registerProduct("84133","Frasco Table Lamp"," ");
            registerProduct("84337","Coffee Mug With Lid (Brown)"," ");
            registerProduct("84338","Coffee Mug With Lid (Black)"," ");
            registerProduct("84340","Coffee Mug With Lid (Red)"," ");
            registerProduct("84966","Pasta & Salad Server Tongs"," ");
            registerProduct("850714001646","Ashrafi Atta Fine ","10kg");
            registerProduct("8710574033329","Spontex Scouring Sponges (Pack of 5)"," ");
            registerProduct("8712045010846","Enfamil Powder Milk AR ","400g");
            registerProduct("8712045013571","Enfalac Powder Milk Premature ","400g");
            registerProduct("8717644137420","Dove Beauty Soap Fresh Touch ","135g");
            registerProduct("8850007010852","Johnson's Baby Powder Bedtime ","100g");
            registerProduct("8850007011132","Johnson's Baby Powder Mildness ","100g");
            registerProduct("8850007011149","Johnson's Baby Powder Mildness ","200g");
            registerProduct("8850007011163","Johnson's Baby Powder Mildness ","500g");
            registerProduct("8850007011187","Johnson's Baby Powder Blossoms ","200g");
            registerProduct("8850007011200","Johnson's Baby Powder Blossoms ","500g");
            registerProduct("8850007060321","Johnson's Baby Oil Mildness ","200ml");
            registerProduct("8850007060338","Johnson's Baby Oil Mildness ","300ml");
            registerProduct("8850007371632","Stayfree Pads Non-Wing 10's"," ");
            registerProduct("8850007371656","Stayfree Pads With Wings 10's"," ");
            registerProduct("8850007650843","Clean & Clear Foaming Facial Wash ","50ml");
            registerProduct("8850007651000","Clean & Clear Cleanser Acne Clearing ","50g");
            registerProduct("8850007660149","Clean & Clear Toner Oil-Controlling ","50ml");
            registerProduct("8850024103599","Enfalac Powder Milk A+ 1 ","400g");
            registerProduct("8850086193019","Ovaltine Drinking Powder Pouch ","820g");
            registerProduct("8850086194450","Ovaltine Drinking Powder Pouch ","340g");
            registerProduct("8850086244513","Ovaltine Drinking Powder Box ","450g");
            registerProduct("8850124011053","Nestle Coffee Mate Original ","400g");
            registerProduct("8850124025708","Nestle Coffee Mate ","170g");
            registerProduct("8850175012085","Glade Air Freshner Kit T&F Lavender ","12ml");
            registerProduct("8850175012122","Glade Air Freshner Refill Wild Lavender","15ml");
            registerProduct("8850175013303","Glade Air Freshner Kit T & F Morning Fresh ","12ml");
            registerProduct("8850175013310","Glade Air Freshner Refill T & F  Morning Fresh","12ml");
            registerProduct("8850175014843","Glade Hang It Fresh Air Freshner Floral Fresh ","8g");
            registerProduct("8850175014850","Glade Hang It Fresh Air Freshner Cool Fresh ","8g");
            registerProduct("8852756303049","Kellogg's Froot Loops ","180g");
            registerProduct("8852756303087","Kellogg's Rice Krispies ","130g");
            registerProduct("8852756303506","Kellogg's Coco Loops ","170g");
            registerProduct("8852756304046","Kellogg's Froot Loops ","350g");
            registerProduct("8852756304077","Kellogg's Coco Pops ","400g");
            registerProduct("8852756304503","Kellogg's Coco Loops ","330g");
            registerProduct("88531472","Clean & Clear Face Wash Essentials ","100ml");
            registerProduct("886635000017","Kernel Pop Popcorn Butter Blas  (Pack of 3)","90g");
            registerProduct("886635000031","Kernel Pop Popcorn Cheese  (Pack of 3)","90g");
            registerProduct("886635000147","Kernel Pop Popcorn Natural  (pack of 3)","90g");
            registerProduct("8886467100017","Pringles Chips Original ","110g");
            registerProduct("8886467100024","Pringles Chips Sour Cream & Onion ","110g");
            registerProduct("8886467100031","Pringles Chips Cheesy Cheese ","110g");
            registerProduct("8886467100048","Pringles Chips Smoky BBQ ","110g");
            registerProduct("8886467100154","Pringles Chips Pizza ","110g");
            registerProduct("8886467100239","Pringles Chips Original ","47g");
            registerProduct("8886467100253","Pringles Chips Sour Cream & Onion ","47g");
            registerProduct("8886467101205","Pringles Chips Garlic Butter ","110g");
            registerProduct("8901030343636","Vaseline Body Lotion Total Moisture ","100ml");
            registerProduct("8901138500306","Himalaya Tooth Paste Complete Care ","100ml");
            registerProduct("8901138500467","Himalaya Fairness Cream Saffron Natural Glow ","50g");
            registerProduct("8901138505844","Himalaya Shampoo + Conditioner Normal Hair ","400ml");
            registerProduct("8901138506384","Himalaya Skin Cream Nourishing ","200ml");
            registerProduct("8901138506391","Himalaya Skin Cream (All Day Moisturizing) ","250ml");
            registerProduct("8901138507558","Himalaya Hair Cream Protein (Extra Nourishment) ","175ml");
            registerProduct("8901138509231","Himalaya Lip Balm ","10g");
            registerProduct("8901138511029","Himalaya Peel Off Mask Almond & Cucumber ","75ml");
            registerProduct("8901138511593","Himalaya Beauty Soap Almond ","75g");
            registerProduct("8901138511609","Himalaya Beauty Soap Honey ","75g");
            registerProduct("8901138511616","Himalaya Beauty Soap Cucumber ","75g");
            registerProduct("8901138511784","Himalaya Face Wash Purifying Neem ","150ml");
            registerProduct("8901138513061","Himalaya Beauty Soap Honey ","125g");
            registerProduct("8901138513085","Himalaya Beauty Soap Almond ","125g");
            registerProduct("8901138514143","Himalaya Day Cream Energizing ","50g");
            registerProduct("8901138711900","Himalaya Face Wash Gentle Exfoliating ","150ml");
            registerProduct("8901138711962","Himalaya Beauty Soap Neem & Turmeric ","75g");
            registerProduct("8901138713898","Himalaya Intensive Moisturizing Cream ","250ml");
            registerProduct("8901138815721","Himalaya Hair Cream Protein (Soft & Shine) ","140 ml");
            registerProduct("8901138815905","Himalaya Conditioner Protein Dry & Damage ","400ml");
            registerProduct("8901138816575","Himalaya Day Cream Whitening ","50g");
            registerProduct("8901138818777","Himalaya Face Wash Purifying Neem ","50ml");
            registerProduct("8901138819965","Himalaya Face Wash Clear Complexion ","150ml");
            registerProduct("8901138821227","Himalaya Face Wash Gentle Refreshing ","50ml");
            registerProduct("8901138821234","Himalaya Face Wash Clear Complexion ","100ml");
            registerProduct("8901138822286","Himalaya Face Wash Clear Complexion ","50ml");
            registerProduct("8901138825584","Himalaya Toothpaste Sparkly White ","100g");
            registerProduct("8901138825607","Himalaya Tooth Paste Mint Fresh ","100ml");
            registerProduct("8901138831691","Himalaya Hair Cream Anti-Dandruf ","70ml");
            registerProduct("8901138831714","Himalaya Hair Cream Protein Extra Nourishing ","70ml");
            registerProduct("8901138834012","Himalaya Toothpaste Complete Care ","50ml");
            registerProduct("8901138834029","Himalaya toothpaste Mint Fresh ","50ml");
            registerProduct("8901499007940","Kellogg's Chocos Whole Grain ","125g");
            registerProduct("8901499007964","Kellogg's Chocos Whole Grain ","375g");
            registerProduct("8901499007971","Kellogg's Chocos Whole Grain ","700g");
            registerProduct("8901499008176","Kellogg's Corn Flakes ","100g");
            registerProduct("8901499008206","Kellogg's Special K 98% Fat Free ","290g");
            registerProduct("8901499008367","Kellogg's Corn Flakes Real Mango ","300g");
            registerProduct("8901499008398","Kellogg's Muesli Fruit Magic ","500g");
            registerProduct("8901499008404","Kellogg's Muesli Nuts Delight ","550g");
            registerProduct("8901499008428","Kellogg's Muesli Fruit Nut ","500g");
            registerProduct("8901499008701","Kellogg's Special K 98% Fat Free ","900g");
            registerProduct("8901499008817","Kellogg's Chocos Crunchy Bites ","390g");
            registerProduct("8901526203550","Garnier Men Fairness Moisturizer PowerLight ","50g");
            registerProduct("8901526203574","Garnier Men Face Wash PowerLight ","100g");
            registerProduct("8901526204304","Garnier Men Face Wash PowerLight ","50g");
            registerProduct("8901526205578","Garnier Men Face Wash Oil Clear ","50g");
            registerProduct("8934868058441","Comfort Fabric Softner Morning Fresh (Blue) ","200ml");
            registerProduct("8934868058472","Comfort Fabric Softner Lily Fresh (Pink) ","200ml");
            registerProduct("8934868084662","Vim Dishwash Liquid Lemon ","500ml");
            registerProduct("8934868084686","Vim Dishwash Liquid Lime ","500ml");
            registerProduct("895120001002","Freshmate Chicken Aaloo Qeema"," ");
            registerProduct("895120001026","Freshmate Lakhnawy Kabab"," ");
            registerProduct("895120001057","Freshmate Chicken Achari"," ");
            registerProduct("895120001125","Freshmate Chicken Karahi ","275g");
            registerProduct("895120001132","Freshmate Shahi Qorma"," ");
            registerProduct("895120001149","Freshmate Chicken Hara Masala"," ");
            registerProduct("895120001163","Freshmate Chicken Handi"," ");
            registerProduct("895120001170","Freshmate Delhi Shahi Nehari"," ");
            registerProduct("895120001200","Freshmate Chicken Nehari"," ");
            registerProduct("895120001293","Freshmate Chicken Biryani"," ");
            registerProduct("895120001330","Freshmate Chicken Haleem"," ");
            registerProduct("895120001385","Freshmate Chicken Shami Kabab"," ");
            registerProduct("895120001583","Freshmate Chat Masala Chatkhara ","100g");
            registerProduct("895120001590","Freshmate Raita mix Podina ","80g");
            registerProduct("895120001743","Freshmate Raita mix Zeera ","80g");
            registerProduct("895120001859","Freshmate Fruit Chaat Masala Chatkhara ","100g");
            registerProduct("8961003010118","LU Prince Chocolate (Family Pack)"," ");
            registerProduct("8961003010217","LU Tuc (Family Pack)"," ");
            registerProduct("8961003010316","LU Candi (Family Pack)"," ");
            registerProduct("8961003010521","Lu Gala Egg Family Pack"," ");
            registerProduct("8961003011016","LU Zeera Plus (Family Pack)"," ");
            registerProduct("8961003011733","LU Bakeri Coconut (Family Pack)"," ");
            registerProduct("8961003011740","LU Bakeri Butter (Family Pack)"," ");
            registerProduct("8961003011771","LU Bakeri Nan Khatai (Family Pack)"," ");
            registerProduct("8961003011832","LU Wheatable (Family Pack)"," ");
            registerProduct("8961003014017","LU Bistiks (Family Pack)"," ");
            registerProduct("8961003030116","LU Prince Chocolate Snack Pack (Pack of 6)"," ");
            registerProduct("8961003030215","LU Tuc Snack Pack (Pack Of 6)"," ");
            registerProduct("8961003030345","LU Candi Snack Pack (Pack of 6)"," ");
            registerProduct("8961003030529","Lu Gala Egg Snack Pack (Pack Of 6)"," ");
            registerProduct("8961003031038","Lu Zeera Plus Mini Half Roll (Pack Of 12)"," ");
            registerProduct("8961003031526","LU Tiger Snack Pack (Pack of 24)"," ");
            registerProduct("8961003031717","LU Bakeri Classic (Snack Pack)"," ");
            registerProduct("8961003031731","LU Bakeri Coconut Snack pack (Pack of 6)"," ");
            registerProduct("8961003031748","LU Bakeri Butter (Snack pack)"," ");
            registerProduct("8961003031755","Lu Bakeri Nan Khatai Bar Pack (Pack Of 12)"," ");
            registerProduct("8961003031786","LU Bakeri Butter Bar Pack (Pack of 12)"," ");
            registerProduct("8961003031793","LU Bakeri Nan Khatai Snack Pack (Pack of 6)"," ");
            registerProduct("8961003034015","LU Bistiks Snack Pack (Pack Of 6)"," ");
            registerProduct("8961003036019","Lu Oreo Snack Pack (Pack Of 6)"," ");
            registerProduct("8961003050114","LU Prince Chocolate Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003050145","LU Prince Choco Strawberry Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003050213","LU Tuc Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003050527","LU Gala Egg Ticky pack (Pack of 24)"," ");
            registerProduct("8961003051029","LU Zeera Plus Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003051418","LU Candi Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003051715","LU Bakeri Classic Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003051739","LU Bakeri Coconut Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003056017","Lu Oreo Sandwich Biscuit Original Bar Pack"," ");
            registerProduct("8961003072758","LU Bakeri Nan Khatai Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003080364","Lu Candi Orignal Mini Half roll (Pack of 12)"," ");
            registerProduct("8961003513008","LU Prince Strawberry Ticky Pack (Pack of 24)"," ");
            registerProduct("8961003542015","LU Biscuit Snack pack Zeera Plus (Pack of 6)"," ");
            registerProduct("8961005010017","Mitchell's Squash Lemon ","800ml");
            registerProduct("8961005010024","Mitchell's Squash Lemon Barley ","800ml");
            registerProduct("8961005010031","Mitchell's Squash Mango ","800ml");
            registerProduct("8961005010055","Mitchell's Squash Mix Fruit ","800ml");
            registerProduct("8961005010321","Mitchell's Squash Mix Fruit 1.tr","4l");
            registerProduct("8961005030022","Mitchell's Jam Golden Apple ","450g");
            registerProduct("8961005030039","Mitchell's Jam Mango ","450g");
            registerProduct("8961005030046","Mitchell's Jam Mix Fruit ","450g");
            registerProduct("8961005030053","Mitchell's Jam Golden Apple Diet ","325g");
            registerProduct("8961005030091","Mitchell's Jam Strawberry Diet ","325g");
            registerProduct("8961005030107","Mitchell's Jam Mix Fruit Diet ","325g");
            registerProduct("8961005030411","Mitchell's Jam Mango ","200g");
            registerProduct("8961005030428","Mitchell's Jam Mixed Fruit ","200g");
            registerProduct("8961005030435","Mitchell's Jam Strawberry ","200g");
            registerProduct("8961005030497","Mitchell's Jam Strawberry ","340g");
            registerProduct("8961005030503","Mitchell's Jam Pineapple ","340g");
            registerProduct("8961005030510","Mitchell's Jam Apricot ","340g");
            registerProduct("8961005030589","Mitchell's Jam Blackcurrant ","340g");
            registerProduct("8961005040014","Mitchell's Jelly Apple ","450g");
            registerProduct("8961005040045","Mitchell's Jelly Strawberry ","450g");
            registerProduct("8961005040052","Mitchell's Jelly Raspberry ","450g");
            registerProduct("8961005040304","Mitchell's Jelly Apple ","200g");
            registerProduct("8961005050013","Mitchell's Jam Golden Mist ","450g");
            registerProduct("8961005050044","Mitchell's Jam Golden Mist Diet ","325g");
            registerProduct("8961005050211","Mitchell's Marmalade Golden Mist ","200g");
            registerProduct("8961005060012","Mitchell's Tomato Ketchup ","300g");
            registerProduct("8961005060036","Mitchell's Sauce Chilli Garlic ","300g");
            registerProduct("8961005060104","Mitchell's Sauce Imli ","300g");
            registerProduct("8961005060289","Mitchell's Sauce Green Chilli ","300g");
            registerProduct("8961005060616","Mitchell's Sauce Chilli Garlic Pouch ","800g");
            registerProduct("8961005060623","Mitchell's Tomato Ketchup Pouch ","800g");
            registerProduct("8961005060630","Mitchell's Tomato Ketchup Pouch ","400g");
            registerProduct("8961005060647","Mitchell's Sauce Chilli Garlic Pouch ","400g");
            registerProduct("8961005070011","Mitchell's Pickle Kasundi Mango ","340g");
            registerProduct("8961005070028","Mitchell's Pickle Mango ","340g");
            registerProduct("8961005070066","Mitchell's Pickle Garlic ","340g");
            registerProduct("8961005070097","Mitchell's Pickle Green Chilli ","340g");
            registerProduct("8961005070219","Mitchell's Pickle Mango Jar ","900g");
            registerProduct("8961005070226","Mitchell's Pickle Mixed Jar ","900g");
            registerProduct("8961005080072","Mitchell's Vinegar Synthetic White ","310ml");
            registerProduct("8961005080096","Mitchell's Vinegar Synthetic White PET Bot ","800ml");
            registerProduct("8961005090019","Mitchell's Jam Apple Tin 1.","05kg");
            registerProduct("8961005090026","Mitchell's Jam Golden Mist Tin 1.","05kg");
            registerProduct("8961005090033","Mitchell's Jam Mix Fruit Tin 1.","05kg");
            registerProduct("8961005090040","Mitchell's Jam Mango Tin 1.","05kg");
            registerProduct("8961005090088","Mitchell's Jam Strawberry Tin 1.","05kg");
            registerProduct("8961005100015","Mitchell's Garden Peas ","450g");
            registerProduct("8961005100022","Mitchell's Garden Peas ","850g");
            registerProduct("8961005100039","Mitchell's Sweet Corn ","450g");
            registerProduct("8961005100046","Mitchell's Sweet Corn ","850g");
            registerProduct("8961005100121","Mitchell's Chick Peas ","440g");
            registerProduct("8961005120044","Mitchell's Fruit Cocktail ","850g");
            registerProduct("8961005120075","Mitchell's Pinneapple Rings ","565g");
            registerProduct("8961006010016","Rose Petal Tissue Luxury 100's"," ");
            registerProduct("8961006010054","Rose Petal Tissue Multi Colour 150's"," ");
            registerProduct("8961006010078","Rose Petal Tissue Supreme 100's"," ");
            registerProduct("8961006010115","Rose Petal Paper Towel Twin Pack"," ");
            registerProduct("8961006010283","Rose Petal Tissue Hi-Jeen 150's"," ");
            registerProduct("8961006020015","Rose Petal Toilet Roll Single"," ");
            registerProduct("8961006020022","Rose Petal Toilet Roll Twin Pack"," ");
            registerProduct("8961006040013","Rose Petal Table Napkins 50's"," ");
            registerProduct("8961006040020","Rose Petal Paper Towel Single"," ");
            registerProduct("8961006040044","Rose Petal Tissue Pop Up 150's"," ");
            registerProduct("8961008203744","Nestle Fruita Vitals Red Grapes","1L");
            registerProduct("8961008203782","NESTLE FRUITA VITALS - PEACH ()","1L");
            registerProduct("8961008205113","Nestle Mineral Water 1.5tr","1500ML");
            registerProduct("8961008205137","Nestle Mineral Water","500ml");
            registerProduct("8961008206400","Milk Pak Milk tra","1l");
            registerProduct("8961008207421","Everyday Tea Whitener","1kg");
            registerProduct("8961008270029","Nestle Mineral Water","500ml");
            registerProduct("8961014000320","Lifebuoy Hand Wash Care Pump ","220ml");
            registerProduct("8961014000337","Lifebuoy Hand Wash Total Pump ","220ml");
            registerProduct("8961014000573","Lifebuoy Hand Wash Care Pouch ","200ml");
            registerProduct("8961014000887","Lifebuoy Hand Wash Active Fresh Pouch ","200ml");
            registerProduct("8961014001235","Lifebuoy Soap Total ","115g");
            registerProduct("8961014001266","Lifebuoy Soap Care ","115g");
            registerProduct("8961014001297","Lifebuoy Soap Nature ","115g");
            registerProduct("8961014001518","Lifebuoy Soap Lemon Fresh ","115g");
            registerProduct("8961014001792","Domex Toilet Expert Original ","500ml");
            registerProduct("8961014001976","Vim Dishwash Soap Long Bar ","285g");
            registerProduct("8961014002065","Lifebuoy Hand Wash Kitchen Fresh Pump ","220ml");
            registerProduct("8961014002089","Vim Dishwash Liquid Lime ","250ml");
            registerProduct("8961014002096","Vim Dishwash Liquid Lime ","250ml");
            registerProduct("8961014006650","Dove Conditioner Split End Rescue ","180ml");
            registerProduct("8961014006728","Lux Soap Fresh Splash ","115g");
            registerProduct("8961014006766","Lux Soap Velvet Touch (White) ","115g");
            registerProduct("8961014006773","Lux Soap Velvet Touch (White) ","150g");
            registerProduct("8961014006803","Lux Soap Soft Touch (Pink) ","115g");
            registerProduct("8961014006810","Lux Soap Soft Touch (Pink) ","150g");
            registerProduct("8961014006834","Lux Soap Purple Lotus & Cream ","115g");
            registerProduct("8961014006865","Lux Soap Nature Pure ","115g");
            registerProduct("8961014007213","Dove Conditioner Straight & Silky Tube ","180ml");
            registerProduct("8961014007220","Dove Conditioner Dryness Care Tube ","180ml");
            registerProduct("8961014007268","Dove Shampoo Daily Shine ","175ml");
            registerProduct("8961014007275","Dove Shampoo Dryness Care ","175ml");
            registerProduct("8961014007305","Dove Shampoo Daily Shine ","360ml");
            registerProduct("8961014007398","Dove Shampoo Hair Fall Rescue ","175ml");
            registerProduct("8961014011081","Sunsilk Shampoo Black Shine ","200ml");
            registerProduct("8961014011128","Sunsilk Shampoo Hair Fall Solution ","400ml");
            registerProduct("8961014011135","Sunsilk Shampoo Thick & Long ","200ml");
            registerProduct("8961014011159","Sunsilk Shampoo Soft & Smooth ","200ml");
            registerProduct("8961014011166","Sunsilk Shampoo Soft & Smooth ","400ml");
            registerProduct("8961014011272","Clear Men Shampoo Cool Black ","200ml");
            registerProduct("8961014011319","Clear Shampoo Soft & Shiny ","200ml");
            registerProduct("8961014011340","Clear Men Shampoo Hair Fall Defense ","200ml");
            registerProduct("8961014011623","Vim Dishwash Powder ","900g");
            registerProduct("8961014019216","Vim Dishwash Long Bar Anti Germ ","285g");
            registerProduct("8961014027891","Sunsilk Conditioner Thick & Long ","180ml");
            registerProduct("8961014028997","Sunsilk Conditioner Black Shine Tube ","180ml");
            registerProduct("8961014029390","Sunsilk Shampoo Perfect Straight ","200ml");
            registerProduct("8961014029406","Sunsilk Shamp Perfect Straight ","400ml");
            registerProduct("8961014029413","Sunsilk Conditioner Perfect Straight ","180ml");
            registerProduct("8961014035575","Fair & Lovely Cream Max Fairness For Men ","50g");
            registerProduct("8961014035810","Fair & Lovely Cream Advance M.Vitamin ","50g");
            registerProduct("8961014035858","Fair & Lovely Cream Multi Vitamin Jar ","70ml");
            registerProduct("8961014036046","Vaseline Body Lotion H.White Lightening Instant Fair ","200ml");
            registerProduct("8961014036053","Vaseline Body Lotion Aloe Fresh ","200ml");
            registerProduct("8961014036459","Fair & Lovely Cream Winter Fairness ","70g");
            registerProduct("8961014036466","Ponds Moisturizing Lotion Vitamin E ","100ml");
            registerProduct("8961014036473","Ponds Moisturizing Lotion Vitamin E ","200ml");
            registerProduct("8961014036817","Vaseline Body Lotion Total Moisture ","250ml");
            registerProduct("8961014046052","CloseUp Tooth Gel Red Hot ","125g");
            registerProduct("8961014046328","Clear Shampoo Ice Cool Menthol ","200ml");
            registerProduct("8961014046342","Clear Men Shampoo Cool Sport Menthol ","200ml");
            registerProduct("8961014105667","Lipton Green Tea Bags Mint (25 Tea Bags)"," ");
            registerProduct("8961014106169","Lipton Yellow Label Tea ","190g");
            registerProduct("8961014106176","Lipton Yellow Label Tea ","950g");
            registerProduct("8961014106183","Lipton Yellow Label Tea Bags (100 Tea bags)"," ");
            registerProduct("8961014106220","Lipton Yellow Label Tea ","95g");
            registerProduct("8961014106244","Lipton Yellow Label Tea Mega Daane Jar ","475g");
            registerProduct("8961014106251","Lipton Tea Yellow Label Poly Bag ","475g");
            registerProduct("8961014106282","Lipton Yellow Label Tea Bags (50 Tea bags)"," ");
            registerProduct("8961014106305","Lipton Green Tea Bags (25 Tea Bags)"," ");
            registerProduct("8961014106312","Lipton Green Tea Bags Jasmine (25 Tea Bags)"," ");
            registerProduct("8961014106329","Lipton Green Tea Bags Lemon (25 Tea Bags)"," ");
            registerProduct("8961014177510","Knorr Noodle Chatt Patta ","66g");
            registerProduct("8961014177527","Knorr Noodle Chicken ","66g");
            registerProduct("8961014178487","Knorr Noodle Achari Masti ","66g");
            registerProduct("8961014183016","Rafhan Corn Oil Tin tr","2l");
            registerProduct("8961014183023","Rafhan Corn Oil Tin tr","4l");
            registerProduct("8961014183030","Rafhan Corn Oil Tin 9.tr","5l");
            registerProduct("8961014184471","Rafhan Jelly Powder Banana ","80g");
            registerProduct("8961014184488","Rafhan Jelly Powder Orange ","80g");
            registerProduct("8961014184495","Rafhan Jelly Powder Strawberry ","80g");
            registerProduct("8961014184525","Rafhan Jelly Powder Mango ","80g");
            registerProduct("8961014184532","Rafhan Custard Vanilla Box ","300g");
            registerProduct("8961014184549","Rafhan Custard Banana Box ","300g");
            registerProduct("8961014184556","Rafhan Custard Mango ","300g");
            registerProduct("8961014184563","Rafhan Custard Strawberry Box ","300g");
            registerProduct("8961014184662","Rafhan Cornflour Box ","300g");
            registerProduct("8961014189223","Knorr Yakhni Chicken 5's"," ");
            registerProduct("8961100310609","Hashmi Ispaghol Husk ","100g");
            registerProduct("8961100310654","Hashmi Ispaghol Husk  Jar","140g");
            registerProduct("8961100310951","Hashmi Ispaghol Husk  Jar","290g");
            registerProduct("8961100311156","Hashmi Ispaghol Husk Jar ","85g");
            registerProduct("8964000006009","Soya Supreme Ghee Poly Bag ","1kg");
            registerProduct("8964000006054","Soya Supreme Cooking Oil Poly Bag tr","1l");
            registerProduct("8964000019009","Scotch Brite Scouring Pad Large"," ");
            registerProduct("8964000019061","Scotch Brite Leminate Nail Saver"," ");
            registerProduct("8964000019085","Scotch Brite Floor Cloth Large"," ");
            registerProduct("8964000019115","Scotch Brite Leminate Bumper Saver"," ");
            registerProduct("8964000019160","Scotch Brite Leminate Lajawab Pack"," ");
            registerProduct("8964000019344","Scotch Brite Stainless Steel Spiral Pouch"," ");
            registerProduct("8964000019535","Scotch Brite Sponge Cloth (pack of 3)"," ");
            registerProduct("8964000019542","Scotch Brite Sponge Cloth (Pack of 5)"," ");
            registerProduct("8964000019603","Scotch Brite Stainless Steel Spiral Jumbo"," ");
            registerProduct("8964000019610","Scotch Brite Scouring Pad Non-Scratch Large"," ");
            registerProduct("8964000019627","Scotch Brite Leminate Non-Scratch Bumper Saver (Pack of 2)"," ");
            registerProduct("8964000019689","Scotch Brite Leminate Nail Saver Value Pack"," ");
            registerProduct("8964000020081","Shangrila Sauce Soya ","300ml");
            registerProduct("8964000020098","Shangrila Sauce Soya ","800ml");
            registerProduct("8964000020180","Shangrila Sauce Chilli ","300ml");
            registerProduct("8964000020197","Shangrila Sauce Chilli ","800ml");
            registerProduct("8964000020289","Shangrila Vinegar Synthetic White ","300ml");
            registerProduct("8964000020296","Shangrila Vinegar Synthetic White ","800ml");
            registerProduct("8964000020357","Shangrila Tomato Ketchup Pouch ","500g");
            registerProduct("8964000020364","Shangrila Tomato Ketchup Pouch ","1kg");
            registerProduct("8964000020425","Shangrila Tomato Ketchup 4.","4kg");
            registerProduct("8964000020432","Shangrila Tomato Ketchup 1.","8kg");
            registerProduct("8964000020487","Shangrila Sauce Chilli Garlic Pouch ","500g");
            registerProduct("8964000020531","Shangrila Sauce Chilli Garlic Pouch ","1kg");
            registerProduct("8964000020579","Shangrila Sauce Imli Ginger Pouch ","500g");
            registerProduct("8964000020616","Shangrila Pickle Mix ","320g");
            registerProduct("8964000020678","Shangrila Pickle Mix Pouch ","500g");
            registerProduct("8964000020685","Shangrila Pickle Mix Pouch ","1kg");
            registerProduct("8964000020760","Shangrila Pickle Mix Jar ","1kg");
            registerProduct("8964000020777","Shangrila Pickle Mango Jar ","1kg");
            registerProduct("8964000020807","Shangrila Pickle Hyderabadi ","320g");
            registerProduct("8964000020869","Shangrila Pickle Mango Pouch ","500g");
            registerProduct("8964000021545","Shangrila Sauce Trio Pack  (Pack of 3)","800ml");
            registerProduct("8964000021576","Shangrila Sauce Trio Pack  (Pack of 3)","300ml");
            registerProduct("8964000021583","Shangrila Sauce Trio Pack  (Pack of 3)","120ml");
            registerProduct("8964000021880","Shangrila Tomato Ketchup ","350g");
            registerProduct("8964000021897","Shangrila Tomato Ketchup ","5kg");
            registerProduct("8964000021927","Shangrila Sauce Chilli Garlic 4.","4kg");
            registerProduct("8964000034002","Salman's Jam Apple ","450g");
            registerProduct("8964000034019","Salman's Jelly Apple ","450g");
            registerProduct("8964000034026","Salman's Jam Apricot ","450g");
            registerProduct("8964000034033","Salman's Jam Blackcurrant ","450g");
            registerProduct("8964000034040","Salman's Jam Cherry ","450g");
            registerProduct("8964000034057","Salman's Marmalade Citrus ","450g");
            registerProduct("8964000034064","Salman's Jam Black Fig ","450g");
            registerProduct("8964000034101","Salman's Jam Mango ","450g");
            registerProduct("8964000034118","Salman's Jam Mix Fruit ","450g");
            registerProduct("8964000034125","Salman's Jam Peach ","450g");
            registerProduct("8964000034132","Salman's Jam Pineapple ","450g");
            registerProduct("8964000034149","Salman's Jam Strawberry ","450g");
            registerProduct("8964000034699","Salman's Sauce Pizza ","380g");
            registerProduct("8964000034712","Salman's Syrup Chocolate ","623g");
            registerProduct("8964000034729","Salman's Syrup Strawberry ","623g");
            registerProduct("8964000050095","Shezan Sauce Soya ","390ml");
            registerProduct("8964000050101","Shezan Sauce Chilli ","400ml");
            registerProduct("8964000050118","Shezan Sauce Chilli ","825g");
            registerProduct("8964000050132","Shezan Sauce Soya ","735ml");
            registerProduct("8964000051030","Shezan Tomato Ketchup 4.","4kg");
            registerProduct("8964000051078","Shezan Tomato Ketchup Pouch ","500g");
            registerProduct("8964000051269","Shezan Jam Mango ","2kg");
            registerProduct("8964000051276","Shezan Jam Apple ","2kg");
            registerProduct("8964000051283","Shezan Jam Mix Fruit ","2kg");
            registerProduct("8964000051290","Shezan Jam Sweet Orange ","2kg");
            registerProduct("8964000051580","Shezan Vinegar Synthetic White ","800ml");
            registerProduct("8964000051641","Shezan Sweet Corn ","850g");
            registerProduct("8964000052716","Shezan Sauce Chilli Garlic Pouch ","1kg");
            registerProduct("8964000052938","Shezan Sauce Chilli Garlic Pouch ","500g");
            registerProduct("8964000061015","French Mayonnaise ","300ml");
            registerProduct("8964000061114","French Mayonnaise Pouch ","500ml");
            registerProduct("8964000061886","Young's Honey ","1kg");
            registerProduct("8964000064054","Fauji Corn Flakes ","500g");
            registerProduct("8964000064061","Fauji Corn Flakes ","250g");
            registerProduct("8964000064146","Fauji Wheat Flakes ","250g");
            registerProduct("8964000064153","Fauji Wheat Porridge ","250g");
            registerProduct("8964000064191","Fauji Bran Flakes ","300g");
            registerProduct("8964000064207","Fauji Rice Flakes ","250g");
            registerProduct("8964000064214","Fauji Barley Porridge ","250g");
            registerProduct("8964000064290","Fauji Frosted Flakes ","250g");
            registerProduct("8964000064405","Fauji Corn Flakes Choco ","250g");
            registerProduct("8964000064474","Fauji Corn Flakes Honey ","250g");
            registerProduct("8964000064610","Fauji Pearl Barley ","300g");
            registerProduct("8964000064627","Fauji Choco Cups New ","250g");
            registerProduct("8964000064788","Fauji Chocolate Pops ","250g");
            registerProduct("8964000064863","Fauji Honey Pops ","250g");
            registerProduct("8964000064917","Fauji Frootooz ","250g");
            registerProduct("8964000064955","Fauji Choco Rings ","250g");
            registerProduct("8964000074008","Adam's Cheddar Cheese ","200g");
            registerProduct("8964000074015","Adam's Cheddar Cheese ","400g");
            registerProduct("8964000074053","Adam's Mozzarella Cheese ","200g");
            registerProduct("8964000074107","Adam's Pizza Cheese ","400g");
            registerProduct("8964000074190","Adam's Cottage Cheese ","200g");
            registerProduct("8964000090008","Cheetos Chips Cheese Ocean Safari ","22g");
            registerProduct("8964000090220","Lay's Chips Salted ","14g");
            registerProduct("8964000090237","Lay's Chips Masala ","14g");
            registerProduct("8964000090251","Lay's Chips French Cheese ","14g");
            registerProduct("8964000090268","Lay's Chips Classic Salted ","45g");
            registerProduct("8964000090275","Lay's Chips Masala ","45g");
            registerProduct("8964000090282","Lay's Chips French Cheese ","45g");
            registerProduct("8964000090299","Lay's Chips Classic Salted ","78g");
            registerProduct("8964000090305","Lay's Chips Ketchup Tango ","16g");
            registerProduct("8964000090329","Lay's Chips French Cheese ","78g");
            registerProduct("8964000090343","Lay's Chips Masala ","78g");
            registerProduct("8964000090411","Lay's Chips Salted ","168g");
            registerProduct("8964000090428","Lay's Chips Masala ","168g");
            registerProduct("8964000090435","Lay's Chips French Cheese ","168g");
            registerProduct("8964000090893","Cheetos Chips Ketchup ","82g");
            registerProduct("8964000101087","7UP Drink ","500ml");
            registerProduct("8964000101094","Mountain Dew Drink ","500ml");
            registerProduct("8964000101100","Mountain Dew Drink tr","1l");
            registerProduct("8964000101117","Mountain Dew Drink 1.tr","5l");
            registerProduct("8964000101124","Mountain Dew Drink Jumbo 2.tr","25l");
            registerProduct("8964000101315","Mirinda Drink tr","1l");
            registerProduct("8964000101322","Mirinda Drink Orange Jumbo 2.tr","25l");
            registerProduct("8964000101377","7UP Drink tr","1l");
            registerProduct("8964000101414","Slice Fruit Drink Mango tr","1l");
            registerProduct("8964000101476","Pepsi Drink Can Local ","250ml");
            registerProduct("8964000101506","Mirinda Drink Can Local ","250ml");
            registerProduct("8964000101513","Mountain Dew Drink Can Local ","250ml");
            registerProduct("8964000101575","7UP Drink Jumbo 2.tr","25l");
            registerProduct("8964000101582","7UP Drink Can ","250ml");
            registerProduct("8964000101605","Mirinda Drink Orange ","500ml");
            registerProduct("8964000101704","Sting Energy Drink Berry Blast ","500ml");
            registerProduct("8964000101957","Slice Fruit Drink Mango Tetra ","200ml");
            registerProduct("8964000103098","Pepsi Drink 1.tr","75l");
            registerProduct("8964000122105","Rooh Afza Sharbat ","800ml");
            registerProduct("8964000122112","Rooh Afza Sharbat 1.tr","5l");
            registerProduct("8964000122396","Hamdard Ispaghol Husk Mint Jar ","120g");
            registerProduct("8964000122402","Hamdard Ispaghol Husk Orange Jar ","120g");
            registerProduct("8964000144930","Onaaj Chakki Atta ","10kg");
            registerProduct("8964000185094","Shezan Vinegar Synthetic White ","400ml");
            registerProduct("8964000263075","Pakola Milk Rose ","250ml");
            registerProduct("8964000263112","Pakola Milk Ice Cream Soda ","250ml");
            registerProduct("8964000263150","Pakola Milk Strawberry ","250ml");
            registerProduct("8964000263174","Pakola Milk Zafrani ","250ml");
            registerProduct("8964000263235","Pakola Milk Chocolate ","250ml");
            registerProduct("8964000345061","Canderel Sweetener Tablets (100 Tablets)"," ");
            registerProduct("8964000345078","Canderel Sweetener Tablets (200 Tablets)"," ");
            registerProduct("8964000345443","Canderel Sweetener For Cooking & Baking ","125g");
            registerProduct("8964000345450","Canderel Sweetener Powder Pouch ","100g");
            registerProduct("8964000345658","Vitamin Water Peach Bottle ","500ml");
            registerProduct("8964000345665","Vitamin Water Apple Bottle ","500ml");
            registerProduct("8964000345672","Vitamin Water Red Berries Bottle ","500ml");
            registerProduct("8964000345719","Vitamin Water Lychee Bottle ","500ml");
            registerProduct("8964000345733","Vitamin Water Orange Bottle ","500ml");
            registerProduct("8964000345740","Vitamin Water Pomegranate Bottle ","500ml");
            registerProduct("8964000345764","Vitamin Water Lychee Bottle ","300ml");
            registerProduct("8964000345771","Vitamin Water Orange Bottle ","300ml");
            registerProduct("8964000345788","Vitamin Water Lemon-Lime Bottle ","300ml");
            registerProduct("8964000345795","Vitamin Water Pomegranate Bottle ","300ml");
            registerProduct("8964000345801","Vitamin Water Apple Bottle ","300ml");
            registerProduct("8964000345818","Vitamin Water Peach Bottle ","300ml");
            registerProduct("8964000345825","Vitamin Water Red Berries Bottle ","300ml");
            registerProduct("8964000462034","Garnier Face Wash Light ","100ml");
            registerProduct("8964000462041","Garnier Face Wash Light ","50ml");
            registerProduct("8964000462249","Garnier Color Naturals Hair Color Kit# 3 Box ","40g");
            registerProduct("8964000462263","Garnier Color Naturals Hair Color Kit# 4 Box"," ");
            registerProduct("8964000499160","Caroline Phenyle Concentrated ","450ml");
            registerProduct("8964000499177","Caroline Phenyle Concentrated ","225ml");
            registerProduct("8964000499207","Caroline Phenyle tr","3l");
            registerProduct("8964000843109","Shangrila Sauce Green Chilli Pouch ","500g");
            registerProduct("8964000843147","Shangrila Tomato Ketchup Hot & Spicy Pouch ","500ml");
            registerProduct("8964000954041","Harpic Toilet Cleaner Orange ","500ml");
            registerProduct("8964000954058","Harpic Toilet Cleaner Orange ","750ml");
            registerProduct("8964000954454","Veet Hair Removing Cream Brightening Sensitive ","25g");
            registerProduct("8964000954461","Veet Hair Removing Cream Brightening Normal ","25g");
            registerProduct("8964000954478","Veet Hair Removing Cream Brightening Sensitive ","50g");
            registerProduct("8964000954485","Veet Hair Removing Cream Brightening Normal ","50g");
            registerProduct("8964000954492","Veet Hair Removing Cream Brightening Sensitive Skin ","100g");
            registerProduct("8964000954508","Veet Hair Removing Cream Brightening Normal Skin ","100g");
            registerProduct("8992696416062","Polo Candy Peppermint ","24g");
            registerProduct("8992696417250","Fox's Candy Berries Pouch ","90g");
            registerProduct("8992696418936","Fox's Candy Fruits Pouch ","90g");
            registerProduct("8992696418967","Fox's Candy Mint Pouch ","90g");
            registerProduct("8992696419162","Nescafe Coffee Classic ","2g");
            registerProduct("9002490100490","Red Bull Drink Can  (Pack of 4)","250ml");
            registerProduct("90162602","Red Bull Drink Can ","250ml");
            registerProduct("90376238","Red Bull Drink Can Sugar Free ","250ml");
            registerProduct("90376467","Red Bull Drink Can ","355ml");
            registerProduct("9066085415291","Happy Cow Portion Cheese Regular (Pack of 24)"," ");
            registerProduct("9300607741711","Clean & Clear Toner Oil-Controlling ","100ml");
            registerProduct("9300607741735","Clean & Clear Face Wash Essentials  Pump","150ml");
            registerProduct("9555222600381","Glade Hang It Fresh Air Freshner Wild Lavender ","8g");
            registerProduct("9555222600398","Glade Hang It Fresh Air Freshner Spring Jasmine ","8g");
            registerProduct("9556001013835","Nido Powder Milk 3+ Tin 1.","8kg");
            registerProduct("9556006000212","Johnson's Baby Lotion Local ","200ml");
            registerProduct("9556006000250","Johnson's Baby Shampoo Softest Ever ","200ml");
            registerProduct("9556006000281","Johnson's Baby Shampoo Gold ","500ml");
            registerProduct("9556006000304","Johnson's Baby Bath Mildness ","200ml");
            registerProduct("9556006001660","Johnson's Baby Bath Peach ","200ml");
            registerProduct("9556006005569","Johnson's Baby Lotion Aloe Vera ","200ml");
            registerProduct("9556006012086","Johnson's Baby Bath Top-to-Toe ","100ml");
            registerProduct("9556006014547","Johnson's Baby Shampoo Gold ","100ml");
            registerProduct("9556006060001","Johnson's Baby Milk Bath ","200ml");
            registerProduct("9556006060018","Johnson's Baby Milk Bath ","500ml");
            registerProduct("9556006060193","Johnson's Baby Bath Mildness ","100ml");
            registerProduct("9556006060308","Johnson's Baby Milk Bath ","100ml");
            registerProduct("9556006060353","Johnson's Baby Lotion Milk ","200ml");
            registerProduct("9556006060537","Johnson's Baby Lotion Local ","100ml");
            registerProduct("9556006060872","Johnson's Baby Lotion Local ","50ml");
            registerProduct("9556006277775","Clean & Clear Face Wash Essentials ","50ml");
            registerProduct("ID00181","Steak Knife"," ");
            registerProduct("ID00602","Ball Fiber Pillow"," ");
            registerProduct("ID00624","Console Table Lamp"," ");
            registerProduct("ID01534","Pillow Cover Set (Pack of 2)"," ");
            registerProduct("LS00168","Cross Klein Table Lamp"," ");
            registerProduct("LS00171","Sockel Klein Table Lamp"," ");
            registerProduct("MCP000000002","Baisan ","500g");
            registerProduct("MCP000000005","Moong Whole ","500g");
            registerProduct("MCP000000011","Semolina (Suji) ","500g");
            registerProduct("MCP000000015","Mash Whole ","500g");
            registerProduct("MCP000000017","Bajra ","500g");
            registerProduct("MCP000000019","White Chana ","500g");
            registerProduct("MCP000000023","Red Lobia Big ","500g");
            registerProduct("MCP000000024","Black Chana ","500g");
            registerProduct("MCP000000025","Masoor Whole ","500g");
            registerProduct("MCP000000026","Daal Masoor Big ","500g");
            registerProduct("MCP000000027","Daliya ","500g");
            registerProduct("MCP000000028","Canderel Sweetener Box (Pack of 50)"," ");
            registerProduct("MCP000000029","Daal Moong Washed ","500g");
            registerProduct("MCP000000032","Daal Mash Washed ","500g");
            registerProduct("MCP000000037","Daal Chana Medium ","500g");
            registerProduct("MCP000000047","Maida ","500g");
            registerProduct("MCP000000063","Sugar ","1kg");
            registerProduct("MCP000000092","Roomi Fridge Freshner Datzi"," ");
            registerProduct("MCP000000094","Roomi Air Freshner Hanger Large"," ");
            registerProduct("MCP000000104","Roomi Air Freshner Hanger Small"," ");
            registerProduct("MCP000000140","Shield Feeder Brush"," ");
            registerProduct("MCP000000147","Roomi Air Freshner Block Refill Large"," ");
            registerProduct("MCP000000149","Roomi Air Freshner Block Refill Small"," ");
            registerProduct("MCP000000153","Jharoo Phool Large ","400g");
            registerProduct("MCP000000158","Duster Local Yellow (Pack of 2)"," ");
            registerProduct("MCP000000160","Kitchen Duster (Pack of 2)"," ");
            registerProduct("MCP000000161","Poucha Cloth Large"," ");
            registerProduct("MCP000000162","Jharoo Tinka ","500g");
            registerProduct("MCP000000163","Bake Parlor Macaroni Longer ","400g");
            registerProduct("MCP000000164","Bake Parlor Macaroni Rice Biryani ","250g");
            registerProduct("MCP000000165","Bake Parlor Macaroni Seven Spice ","250g");
            registerProduct("MCP000000166","Bake Parlor Macaroni Ring ","400g");
            registerProduct("MCP000000167","Cloth Roti Large"," ");
            registerProduct("MCP000000168","Bake Parlor Spaghetti Cajun ","250g");
            registerProduct("MCP000000170","Bake Parlor Spaghetti Fajita ","250g");
            registerProduct("MCP000000171","Bake Parlor Pasta Cut Vermicelli ","425g");


        }

    }

    private void registerProduct(String barcode,String pName,String weight) {

        long id = mDbAdapter.createTask(barcode, pName,weight);
        if (id > 0) {
            mRowId = id;

        } else {
            mDbAdapter.updateProduct(barcode, pName,weight);

        }

    }

    public void saveDataInParse() {
        ParseObject gameScore = new ParseObject("Product");
              gameScore.put("Name", "Coffee");
        gameScore.put("Price", "133");
        gameScore.saveInBackground();

    }


    public ParseQuery<ParseObject> getProducts(final String barcode) {
        //region parse code for barcodes
        /*
        ParseQuery<ParseObject> pname = ParseQuery.getQuery("barcodes2");
        pname.whereMatches("barcode", barcode);
         pname.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                System.out.println("success at sometime");
                List<ParseObject> globalScoreList = scoreList;
                if (e == null) {

                    pName=  scoreList.get(0).get("pname").toString();
                } else {
                    System.out.println("Error: " + e.getMessage()); }
            } });

             while(pName.compareTo("nothing") == 0){
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }
            //cidPnames.whereMatchesKeyInQuery("cid","cid", cid.whereMatchesKeyInQuery("pname", "pname", pname.whereMatches("barcode", barcode)));cidPnames.whereMatchesKeyInQuery("cid","cid", cid.whereMatchesKeyInQuery("pname", "pname", pname.whereMatches("barcode", barcode)));

             */

        //endregion

        getProductName(barcode);


/*
        //getProductName(barcode);
        //ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        //query.whereStartsWith("Name", "" + pName);
        query.whereMatches("Name", "(" + pName + ")", "i");
        query.orderByAscending("Price");


  */
        ParseQuery<ParseObject> cid = ParseQuery.getQuery("clusters");
        ParseQuery<ParseObject> cidPnames = ParseQuery.getQuery("clusters");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AllProducts");
        //query.whereMatchesKeyInQuery("pname","pname", cidPnames.whereMatchesKeyInQuery("cid","cid", cid.whereMatchesKeyInQuery("pname", "pname", pname.whereMatches("barcode", barcode))));
        query.whereMatchesKeyInQuery("pname","pname", cidPnames.whereMatchesKeyInQuery("cid","cid",
                cid.whereEqualTo("pname", pName)));
               // cid.whereMatches("pname", "(" + pName + ")", "i")));

        //query2.whereMatchesKeyInQuery("Subcategory", "Subcategory", query.whereMatches("Name", "(" + pName + ")", "i"));
        if(pName.compareTo("nothing") == 0 ){
            Toast.makeText(mainContext.getApplicationContext(), "Nothing found for this barcode!!", Toast.LENGTH_LONG).show();
            System.out.println("Nothing found for this barcode!!");
        }else{
               try{
                    HistoryDbObject.saveSearchRecord(barcode,pName);
               }catch(SQLiteConstraintException e){
                   Toast.makeText(mainContext.getApplicationContext(), "barcode already in the history database!!",
                           Toast.LENGTH_LONG).show();
                   System.out.println("+++++++++++++__(*^%$@#$%^&*()_=== barcode already in the history database!!");
                   return query;
               }catch(Exception ex){
                   return query;

               }
        }

        return query;
    }


    public ParseQuery<ParseObject> getSimilar(String pName) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("AllProducts");
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("AllProducts");
        //query.whereStartsWith("Name", "" + pName);
        //query.whereMatches("Name", "(" + pName + ")", "i");
        //Body Spray
       // query2.whereMatches("Subcategory","(Body Spray)","i");

        query2.whereMatchesKeyInQuery("Subcategory", "Subcategory", query.whereMatches("pname", "(" + pName.trim() + ")", "i"));

        return query2;
    }


    class UPCDatabase extends AsyncTask<Void, Void, Void> {

        private Document htmlDocument;
        private String htmlPageUrl = "http://www.upcdatabase.com/item/";
        private String productName;
        public String barcode;// = "012000014338";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String charset = "UTF-8";
                String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
                String url;
                //System.out.println(google + URLEncoder.encode(searchString, charset));
                //  System.setProperty("http.proxyHost", "10.1.20.18");
                // System.setProperty("http.proxyPort", "9090");
                Document doc = Jsoup.connect(htmlPageUrl + URLEncoder.encode(barcode, charset)).userAgent(userAgent).get();
                Element searchResultCenterContent = doc.select(".data").select("tr").get(2).select("td").get(2);
                System.out.println(searchResultCenterContent.text());
                productName = searchResultCenterContent.text();
                parsedHtmlNode = productName;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mainContext.getApplicationContext(), "Failed to find barcode in upcDatabase!!",
                        Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode = productName;
        }
    }


}
