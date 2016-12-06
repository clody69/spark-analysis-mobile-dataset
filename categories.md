# Categories

## Likes

	df.select(explode($"likes.category").alias("category")).groupBy("category").count.orderBy(desc("count")).show(10000,false)

	+-----------------------------------+-----+
	|category                           |count|
	+-----------------------------------+-----+
	|Community                          |3648 |
	|Musician/Band                      |1941 |
	|Public Figure                      |1919 |
	|Local Business                     |1822 |
	|Company                            |1136 |
	|Food/Beverages                     |814  |
	|Restaurant/Cafe                    |792  |
	|TV Show                            |736  |
	|Movie                              |705  |
	|Website                            |690  |
	|Product/Service                    |650  |
	|Travel/Leisure                     |648  |
	|Media/News/Publishing              |586  |
	|Actor/Director                     |573  |
	|Non-Profit Organization            |566  |
	|Artist                             |561  |
	|Athlete                            |551  |
	|Clothing                           |548  |
	|Personal Blog                      |529  |
	|Fictional Character                |523  |
	|News/Media Website                 |495  |
	|Health/Beauty                      |453  |
	|Entertainment Website              |420  |
	|Arts/Entertainment/Nightlife       |415  |
	|Organization                       |393  |
	|Shopping/Retail                    |370  |
	|Magazine                           |305  |
	|Club                               |300  |
	|Just For Fun                       |293  |
	|Sports Team                        |288  |
	|Local/Travel Website               |281  |
	|Interest                           |275  |
	|App Page                           |232  |
	|Hotel                              |217  |
	|Politician                         |210  |
	|Retail and Consumer Merchandise    |208  |
	|Bar                                |198  |
	|Book                               |191  |
	|Author                             |183  |
	|Entertainer                        |180  |
	|Event Planner                      |180  |
	|Sports/Recreation/Activities       |169  |
	|Cars                               |163  |
	|Spas/Beauty/Personal Care          |160  |
	|Games/Toys                         |159  |
	|Society/Culture Website            |158  |
	|Jewelry/Watches                    |156  |
	|Comedian                           |153  |
	|Consulting/Business Services       |149  |
	|TV Channel                         |143  |
	|Education                          |143  |
	|Attractions/Things to Do           |139  |
	|City                               |139  |
	|Food/Grocery                       |128  |
	|Tours/Sightseeing                  |122  |
	|Writer                             |119  |
	|Radio Station                      |115  |
	|Reference Website                  |105  |
	|Public Places                      |99   |
	|Kitchen/Cooking                    |98   |
	|Sports Venue                       |98   |
	|Government Organization            |97   |
	|Landmark                           |96   |
	|Professional Services              |95   |
	|Small Business                     |94   |
	|Personal Website                   |91   |
	|Other                              |89   |
	|Health/Wellness Website            |86   |
	|Sports League                      |82   |
	|Chef                               |82   |
	|Photographer                       |80   |
	|Concert Tour                       |79   |
	|Museum/Art Gallery                 |79   |
	|Record Label                       |79   |
	|Song                               |76   |
	|School                             |76   |
	|Political Organization             |70   |
	|Recreation/Sports Website          |69   |
	|Movie Theater                      |68   |
	|Arts/Humanities Website            |68   |
	|Cause                              |67   |
	|Amateur Sports Team                |67   |
	|Furniture                          |64   |
	|TV Network                         |63   |
	|Internet/Software                  |63   |
	|Consulting/Business Service        |62   |
	|Automobiles and Parts              |61   |
	|Health/Medical/Pharmaceuticals     |60   |
	|Telecommunication                  |60   |
	|Electronics                        |59   |
	|Journalist                         |59   |
	|Community Organization             |57   |
	|Blogger                            |56   |
	|Wine/Spirits                       |56   |
	|Event Planning/Event Services      |55   |
	|University                         |52   |
	|Pet Services                       |51   |
	|Food                               |50   |
	|Computers/Internet Website         |48   |
	|Album                              |45   |
	|Bank/Financial Institution         |45   |
	|Church/Religious Organization      |42   |
	|Sports Event                       |40   |
	|Dancer                             |40   |
	|Medical & Health                   |40   |
	|Pet Supplies                       |40   |
	|Automotive                         |40   |
	|Software                           |37   |
	|Computers/Technology               |37   |
	|Sport                              |37   |
	|Political Party                    |36   |
	|Professional Service               |36   |
	|Doctor                             |33   |
	|Business Services                  |33   |
	|Business/Economy Website           |30   |
	|Publisher                          |30   |
	|Regional Website                   |29   |
	|Household Supplies                 |29   |
	|Farming/Agriculture                |28   |
	|Music Chart                        |28   |
	|Education Website                  |28   |
	|Movie Character                    |26   |
	|Book Store                         |26   |
	|Movie & Television Studio          |25   |
	|Business Person                    |25   |
	|Science Website                    |25   |
	|Music Video                        |24   |
	|Playlist                           |24   |
	|Home Decor                         |24   |
	|Coach                              |24   |
	|Concert Venue                      |23   |
	|Real Estate                        |23   |
	|Field of Study                     |23   |
	|Engineering/Construction           |22   |
	|Designer                           |22   |
	|Pet                                |21   |
	|Home/Garden Website                |21   |
	|Music                              |20   |
	|Vitamins/Supplements               |20   |
	|Outdoor Gear/Sporting Goods        |19   |
	|Baby Goods/Kids Goods              |19   |
	|Producer                           |19   |
	|Video Game                         |19   |
	|Health/Medical/Pharmacy            |18   |
	|Musical Genre                      |17   |
	|Computers                          |17   |
	|Camera/Photo                       |17   |
	|News Personality                   |16   |
	|Bags/Luggage                       |16   |
	|Appliances                         |16   |
	|Energy                             |15   |
	|Transportation                     |15   |
	|Non-Governmental Organization (NGO)|15   |
	|Cargo & Freight Company            |14   |
	|Insurance Company                  |14   |
	|Monarch                            |14   |
	|Transport/Freight                  |14   |
	|Business Service                   |13   |
	|Newspaper                          |13   |
	|Profession                         |12   |
	|Video                              |12   |
	|Community/Government               |12   |
	|Library                            |11   |
	|Home Improvement                   |11   |
	|Entrepreneur                       |11   |
	|Teens/Kids Website                 |11   |
	|Teacher                            |11   |
	|Book Series                        |11   |
	|Legal/Law                          |11   |
	|Event                              |10   |
	|Country                            |10   |
	|TV Genre                           |10   |
	|TV                                 |9    |
	|Hospital/Clinic                    |9    |
	|State/Province/Region              |9    |
	|Aerospace/Defense                  |8    |
	|Movie Studio                       |8    |
	|Performance Art                    |8    |
	|Animal Breed                       |7    |
	|Episode                            |7    |
	|Music Award                        |7    |
	|Drugs                              |7    |
	|Pet Service                        |6    |
	|Movie Genre                        |6    |
	|Tools/Equipment                    |6    |
	|Government Official                |6    |
	|Art                                |5    |
	|TV/Movie Award                     |5    |
	|Office Supplies                    |5    |
	|Bank/Financial Services            |4    |
	|Patio/Garden                       |4    |
	|Phone/Tablet                       |4    |
	|Biotechnology                      |3    |
	|Building Materials                 |3    |
	|Energy/Utility                     |3    |
	|Lawyer                             |3    |
	|Airport                            |3    |
	|Drink                              |3    |
	|Book Genre                         |2    |
	|TV Season                          |2    |
	|Work Position                      |2    |
	|Theatrical Play                    |2    |
	|Industrials                        |2    |
	|Profile                            |2    |
	|Podcast                            |2    |
	|Scientist                          |2    |
	|Geographical feature               |2    |
	|Article                            |2    |
	|Musical Instrument                 |2    |
	|Language                           |2    |
	|Continent                          |2    |
	|Political Ideology                 |2    |
	|Chemicals                          |2    |
	|OTHER                              |2    |
	|Work Project                       |1    |
	|Animal                             |1    |
	|Competition                        |1    |
	|Transit Stop                       |1    |
	|Middle School                      |1    |
	|Government Website                 |1    |
	|Board Game                         |1    |
	|Religion                           |1    |
	|Food & Beverage Company            |1    |
	|Holiday                            |1    |
	|School Sports Team                 |1    |
	|Diseases                           |1    |
	|Island                             |1    |
	+-----------------------------------+-----+

## Checkins

	df.select(explode($"checkins.types").alias("category")).groupBy("category").count.orderBy(desc("count")).show(10000,false)

	+---------------------------------------------------------------------------------------------------------------------+-----+
	|category                                                                                                             |count|
	+---------------------------------------------------------------------------------------------------------------------+-----+
	|[locality, political]                                                                                                |346  |
	|[point_of_interest, establishment]                                                                                   |181  |
	|[neighborhood, political]                                                                                            |103  |
	|[restaurant, food, point_of_interest, establishment]                                                                 |99   |
	|[store, point_of_interest, establishment]                                                                            |49   |
	|[clothing_store, store, point_of_interest, establishment]                                                            |44   |
	|[lodging, point_of_interest, establishment]                                                                          |37   |
	|[bus_station, transit_station, point_of_interest, establishment]                                                     |36   |
	|[bar, point_of_interest, establishment]                                                                              |34   |
	|[grocery_or_supermarket, food, store, point_of_interest, establishment]                                              |25   |
	|[shopping_mall, point_of_interest, establishment]                                                                    |23   |
	|[gym, health, point_of_interest, establishment]                                                                      |23   |
	|[restaurant, bar, food, point_of_interest, establishment]                                                            |20   |
	|[home_goods_store, store, point_of_interest, establishment]                                                          |14   |
	|[book_store, store, point_of_interest, establishment]                                                                |14   |
	|[church, place_of_worship, point_of_interest, establishment]                                                         |12   |
	|[post_office, finance, point_of_interest, establishment]                                                             |12   |
	|[bar, restaurant, food, point_of_interest, establishment]                                                            |12   |
	|[health, point_of_interest, establishment]                                                                           |11   |
	|[furniture_store, home_goods_store, store, point_of_interest, establishment]                                         |11   |
	|[food, point_of_interest, establishment]                                                                             |10   |
	|[shoe_store, store, point_of_interest, establishment]                                                                |10   |
	|[electronics_store, store, point_of_interest, establishment]                                                         |9    |
	|[food, store, point_of_interest, establishment]                                                                      |9    |
	|[hospital, point_of_interest, establishment]                                                                         |8    |
	|[travel_agency, point_of_interest, establishment]                                                                    |8    |
	|[bank, atm, finance, point_of_interest, establishment]                                                               |8    |
	|[hair_care, point_of_interest, establishment]                                                                        |8    |
	|[night_club, bar, point_of_interest, establishment]                                                                  |7    |
	|[bank, finance, point_of_interest, establishment]                                                                    |7    |
	|[gas_station, point_of_interest, establishment]                                                                      |7    |
	|[car_repair, point_of_interest, establishment]                                                                       |6    |
	|[insurance_agency, point_of_interest, establishment]                                                                 |6    |
	|[city_hall, local_government_office, point_of_interest, establishment]                                               |6    |
	|[train_station, transit_station, point_of_interest, establishment]                                                   |5    |
	|[place_of_worship, point_of_interest, establishment]                                                                 |5    |
	|[bakery, restaurant, food, store, point_of_interest, establishment]                                                  |5    |
	|[dentist, health, point_of_interest, establishment]                                                                  |5    |
	|[pharmacy, health, store, point_of_interest, establishment]                                                          |5    |
	|[school, point_of_interest, establishment]                                                                           |4    |
	|[movie_theater, point_of_interest, establishment]                                                                    |4    |
	|[real_estate_agency, point_of_interest, establishment]                                                               |4    |
	|[museum, point_of_interest, establishment]                                                                           |4    |
	|[colloquial_area, locality, political]                                                                               |4    |
	|[university, point_of_interest, establishment]                                                                       |4    |
	|[car_dealer, store, point_of_interest, establishment]                                                                |4    |
	|[park, premise, point_of_interest, establishment]                                                                    |4    |
	|[beauty_salon, hair_care, point_of_interest, establishment]                                                          |3    |
	|[shoe_store, clothing_store, store, point_of_interest, establishment]                                                |3    |
	|[campground, park, lodging, point_of_interest, establishment]                                                        |3    |
	|[subway_station, transit_station, point_of_interest, establishment]                                                  |3    |
	|[beauty_salon, point_of_interest, establishment]                                                                     |3    |
	|[park, point_of_interest, establishment]                                                                             |3    |
	|[amusement_park, point_of_interest, establishment]                                                                   |3    |
	|[home_goods_store, electronics_store, store, point_of_interest, establishment]                                       |3    |
	|[veterinary_care, point_of_interest, establishment]                                                                  |3    |
	|[night_club, point_of_interest, establishment]                                                                       |3    |
	|[bakery, store, food, point_of_interest, establishment]                                                              |3    |
	|[health, store, point_of_interest, establishment]                                                                    |3    |
	|[bar, lodging, point_of_interest, establishment]                                                                     |3    |
	|[pharmacy, store, health, point_of_interest, establishment]                                                          |3    |
	|[bakery, food, store, point_of_interest, establishment]                                                              |3    |
	|[jewelry_store, store, point_of_interest, establishment]                                                             |3    |
	|[electronics_store, home_goods_store, store, point_of_interest, establishment]                                       |3    |
	|[bar, food, store, point_of_interest, establishment]                                                                 |3    |
	|[night_club, bar, restaurant, food, point_of_interest, establishment]                                                |3    |
	|[car_wash, point_of_interest, establishment]                                                                         |2    |
	|[premise]                                                                                                            |2    |
	|[store, food, point_of_interest, establishment]                                                                      |2    |
	|[lodging, restaurant, food, point_of_interest, establishment]                                                        |2    |
	|[hardware_store, home_goods_store, store, point_of_interest, establishment]                                          |2    |
	|[beauty_salon, spa, point_of_interest, establishment]                                                                |2    |
	|[restaurant, food, bar, point_of_interest, establishment]                                                            |2    |
	|[gym, spa, health, point_of_interest, establishment]                                                                 |2    |
	|[restaurant, food, lodging, point_of_interest, establishment]                                                        |2    |
	|[light_rail_station, transit_station, point_of_interest, establishment]                                              |2    |
	|[bar, cafe, food, store, point_of_interest, establishment]                                                           |2    |
	|[restaurant, lodging, food, point_of_interest, establishment]                                                        |2    |
	|[beauty_salon, hair_care, health, point_of_interest, establishment]                                                  |2    |
	|[lawyer, point_of_interest, establishment]                                                                           |2    |
	|[car_repair, store, point_of_interest, establishment]                                                                |2    |
	|[spa, health, point_of_interest, establishment]                                                                      |2    |
	|[spa, point_of_interest, establishment]                                                                              |2    |
	|[car_rental, point_of_interest, establishment]                                                                       |2    |
	|[doctor, health, point_of_interest, establishment]                                                                   |2    |
	|[funeral_home, point_of_interest, establishment]                                                                     |2    |
	|[premise, point_of_interest, establishment]                                                                          |2    |
	|[spa, gym, health, point_of_interest, establishment]                                                                 |2    |
	|[electrician, store, point_of_interest, establishment]                                                               |1    |
	|[beauty_salon, hair_care, health, store, point_of_interest, establishment]                                           |1    |
	|[shoe_store, health, store, point_of_interest, establishment]                                                        |1    |
	|[bar, liquor_store, restaurant, food, store, point_of_interest, establishment]                                       |1    |
	|[atm, finance, point_of_interest, establishment]                                                                     |1    |
	|[hair_care, health, point_of_interest, establishment]                                                                |1    |
	|[cafe, food, point_of_interest, establishment]                                                                       |1    |
	|null                                                                                                                 |1    |
	|[library, museum, point_of_interest, establishment]                                                                  |1    |
	|[food, store, health, point_of_interest, establishment]                                                              |1    |
	|[beauty_salon, clothing_store, health, spa, hair_care, store, point_of_interest, establishment]                      |1    |
	|[cafe, restaurant, food, store, point_of_interest, establishment]                                                    |1    |
	|[physiotherapist, gym, health, point_of_interest, establishment]                                                     |1    |
	|[pet_store, store, point_of_interest, establishment]                                                                 |1    |
	|[museum, park, point_of_interest, establishment]                                                                     |1    |
	|[restaurant, night_club, bar, food, point_of_interest, establishment]                                                |1    |
	|[stadium, point_of_interest, establishment]                                                                          |1    |
	|[city_hall, atm, local_government_office, finance, point_of_interest, establishment]                                 |1    |
	|[atm, meal_takeaway, restaurant, food, finance, store, point_of_interest, establishment]                             |1    |
	|[book_store, furniture_store, art_gallery, clothing_store, home_goods_store, store, point_of_interest, establishment]|1    |
	|[meal_takeaway, cafe, restaurant, food, point_of_interest, establishment]                                            |1    |
	|[night_club, restaurant, food, point_of_interest, establishment]                                                     |1    |
	|[courthouse, point_of_interest, establishment]                                                                       |1    |
	|[physiotherapist, hospital, doctor, health, point_of_interest, establishment]                                        |1    |
	|[local_government_office, health, point_of_interest, establishment]                                                  |1    |
	|[health, food, clothing_store, store, point_of_interest, establishment]                                              |1    |
	|[meal_delivery, restaurant, food, point_of_interest, establishment]                                                  |1    |
	|[bicycle_store, store, point_of_interest, establishment]                                                             |1    |
	|[clothing_store, shoe_store, home_goods_store, store, point_of_interest, establishment]                              |1    |
	|[bar, food, point_of_interest, establishment]                                                                        |1    |
	|[bar, cafe, restaurant, food, store, point_of_interest, establishment]                                               |1    |
	|[airport, point_of_interest, establishment]                                                                          |1    |
	|[store, spa, point_of_interest, establishment]                                                                       |1    |
	|[clothing_store, shoe_store, store, point_of_interest, establishment]                                                |1    |
	|[embassy, point_of_interest, establishment]                                                                          |1    |
	|[cafe, meal_takeaway, restaurant, food, point_of_interest, establishment]                                            |1    |
	|[premise, parking, point_of_interest, establishment]                                                                 |1    |
	|[campground, rv_park, travel_agency, park, real_estate_agency, lodging, point_of_interest, establishment]            |1    |
	|[cafe, food, store, point_of_interest, establishment]                                                                |1    |
	|[plumber, point_of_interest, establishment]                                                                          |1    |
	|[library, point_of_interest, establishment]                                                                          |1    |
	|[health, hair_care, point_of_interest, establishment]                                                                |1    |
	|[restaurant, bakery, store, food, point_of_interest, establishment]                                                  |1    |
	|[bar, restaurant, food, lodging, point_of_interest, establishment]                                                   |1    |
	|[beauty_salon, spa, health, point_of_interest, establishment]                                                        |1    |
	|[shopping_mall, grocery_or_supermarket, food, store, point_of_interest, establishment]                               |1    |
	|[bar, store, point_of_interest, establishment]                                                                       |1    |
	|[post_office, finance, store, point_of_interest, establishment]                                                      |1    |
	|[gym, health, lodging, point_of_interest, establishment]                                                             |1    |
	|[bar, cafe, restaurant, food, point_of_interest, establishment]                                                      |1    |
	|[laundry, point_of_interest, establishment]                                                                          |1    |
	|[spa, lodging, point_of_interest, establishment]                                                                     |1    |
	|[grocery_or_supermarket, meal_delivery, restaurant, liquor_store, store, food, bar, point_of_interest, establishment]|1    |
	|[meal_takeaway, bar, restaurant, food, point_of_interest, establishment]                                             |1    |
	|[store, car_repair, point_of_interest, establishment]                                                                |1    |
	|[park, health, restaurant, food, store, point_of_interest, establishment]                                            |1    |
	|[restaurant, food, store, point_of_interest, establishment]                                                          |1    |
	|[gas_station, car_repair, point_of_interest, establishment]                                                          |1    |
	|[meal_delivery, meal_takeaway, night_club, bar, restaurant, food, point_of_interest, establishment]                  |1    |
	|[premise, park, point_of_interest, establishment]                                                                    |1    |
	|[meal_takeaway, restaurant, food, point_of_interest, establishment]                                                  |1    |
	|[store, health, point_of_interest, establishment]                                                                    |1    |
	|[parking, point_of_interest, establishment]                                                                          |1    |
	+---------------------------------------------------------------------------------------------------------------------+-----+

## Urls

	df.select(explode($"urls.categories").alias("categories")).select(explode($"categories.label").alias("category")).groupBy("category").count.orderBy(desc("count")).show(1000,false)

	+-------------------------------------------------------------------------------------------+-----+
	|category                                                                                   |count|
	+-------------------------------------------------------------------------------------------+-----+
	|Disastri, Incidenti - Incendio                                                             |137  |
	|Sport                                                                                      |65   |
	|Arte, cultura, intrattenimento - Cinema                                                    |42   |
	|Arte, cultura, intrattenimento - Internet                                                  |41   |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Sistemi Telecomunicazione   |28   |
	|Tempo libero - Gastronomia                                                                 |26   |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Sicurezza                   |16   |
	|Politica - Partiti, Movimenti                                                              |15   |
	|Arte, cultura, intrattenimento - Arti (generico)                                           |14   |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Hardware                    |14   |
	|Economia, affari e finanza - Media - Pubblicità                                            |14   |
	|Arte, cultura, intrattenimento - Moda - Gioielli                                           |14   |
	|Sociale - Famiglia                                                                         |13   |
	|Economia, affari e finanza - Media - Industria televisiva                                  |12   |
	|Economia, affari e finanza - Metallurgia e Meccanica - Automobilistica                     |9    |
	|Economia, affari e finanza - Trasporti - Aerei                                             |8    |
	|Arte, cultura, intrattenimento - Musica                                                    |7    |
	|Ambiente - Natura                                                                          |7    |
	|Arte, cultura, intrattenimento - Musica - Folk                                             |7    |
	|Salute - Ricerca medica                                                                    |7    |
	|Politica - Politica interna - Raccolta dati personali                                      |7    |
	|Arte, cultura, intrattenimento - Moda                                                      |6    |
	|Arte, cultura, intrattenimento - Letteratura                                               |6    |
	|Economia, affari e finanza - Turismo, Tempo libero                                         |5    |
	|Meteo - Allerta meteo                                                                      |5    |
	|Economia, affari e finanza - Informazione d'impresa                                        |5    |
	|Politica - Enti locali                                                                     |5    |
	|Sport - Evento sportivo - Coppa nazionale                                                  |5    |
	|Arte, cultura, intrattenimento - Biblioteche, musei                                        |5    |
	|Politica - Elezioni - Candidato                                                            |5    |
	|Arte, cultura, intrattenimento - Televisione - Soap Opera                                  |5    |
	|Sport - Automobilismo - F1                                                                 |4    |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Tecnologia wireless         |4    |
	|Economia, affari e finanza - Turismo, Tempo libero - Ristorazione, Catering                |4    |
	|Arte, cultura, intrattenimento - Architettura                                              |4    |
	|Meteo                                                                                      |4    |
	|Sport - Evento sportivo - Giochi mondiali                                                  |4    |
	|Economia, affari e finanza - Trasporti - Marittimi, Acque interne                          |4    |
	|Economia, affari e finanza - Media - Industria Radiofonica                                 |4    |
	|Istruzione - Università                                                                    |4    |
	|Giustizia, Criminalità - Giustizia                                                         |4    |
	|Sport - Tennis                                                                             |4    |
	|Giustizia, Criminalità - Giustizia e diritti                                               |3    |
	|Salute - Malattia                                                                          |3    |
	|Scienza, Tecnologia - Ricerca                                                              |3    |
	|Istruzione - Post-Scolare                                                                  |3    |
	|Arte, cultura, intrattenimento - Storia                                                    |3    |
	|Arte, cultura, intrattenimento - Musica - Classica                                         |3    |
	|Storie, Curiosità - Curiosità                                                              |3    |
	|Giustizia, Criminalità - Forze Ordine                                                      |3    |
	|Economia, affari e finanza - Turismo, Tempo libero - Alberghiero                           |3    |
	|Tempo libero - Matrimonio                                                                  |2    |
	|Arte, cultura, intrattenimento - Fotografia                                                |2    |
	|Economia, affari e finanza - Trasporti - Ferrovie                                          |2    |
	|Giustizia, Criminalità - Criminalità Organizzata                                           |2    |
	|Storie, Curiosità - Premi                                                                  |2    |
	|Sport - Ginnastica                                                                         |2    |
	|Arte, cultura, intrattenimento - Musica - hip-hop                                          |2    |
	|Tempo libero - Questioni Spesa                                                             |2    |
	|Arte, cultura, intrattenimento - Animazione                                                |2    |
	|Economia, affari e finanza - Macroeconomia - Obbligazioni                                  |2    |
	|Economia, affari e finanza - Servizi finanziari - Banche                                   |2    |
	|Disastri, Incidenti - Incidenti nei trasporti - Stradali                                   |2    |
	|Lavoro - Impiego                                                                           |2    |
	|Arte, cultura, intrattenimento - Linguaggio                                                |2    |
	|Economia, affari e finanza - Informazione d'impresa - Fusioni, Acquisizioni, Cambi gestione|2    |
	|Sport - Calcio                                                                             |2    |
	|Economia, affari e finanza - Energia - Petrolio e Gas, riserve                             |2    |
	|Sport - Basket                                                                             |2    |
	|Economia, affari e finanza - Media                                                         |2    |
	|Arte, cultura, intrattenimento - Intrattenimento (generico)                                |2    |
	|Economia, affari e finanza - Costruzioni, Proprietà                                        |2    |
	|Economia, affari e finanza - Produzione Chimica                                            |1    |
	|Tempo libero - Giochi                                                                      |1    |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Reti e Connessioni          |1    |
	|Sport - Automobilismo                                                                      |1    |
	|Meteo - Bollettini meteo                                                                   |1    |
	|Salute - Medicina - Occidentale                                                            |1    |
	|Sport - Golf                                                                               |1    |
	|Sociale - Gente, Persone - Infanzia                                                        |1    |
	|Economia, affari e finanza - Costruzioni, Proprietà - Ristrutturazioni                     |1    |
	|Tempo libero - Club, associazioni                                                          |1    |
	|Tempo libero - Bellezza                                                                    |1    |
	|Economia, affari e finanza - Media - On line                                               |1    |
	|Arte, cultura, intrattenimento - Ballo                                                     |1    |
	|Economia, affari e finanza - Trasporti - Stradali                                          |1    |
	|Politica - Referendum                                                                      |1    |
	|Religioni, Fedi - Leader religiosi - Papa                                                  |1    |
	|Istruzione - Insegnamento, Apprendimento - Studente                                        |1    |
	|Salute - Cura - Dieta                                                                      |1    |
	|Economia, affari e finanza - Media - Editoria                                              |1    |
	|Economia, affari e finanza - Turismo, Tempo libero - Articoli sportivi, Tempo libero       |1    |
	|Tempo libero - Svaghi (generico)                                                           |1    |
	|Politica - Elezioni                                                                        |1    |
	|Storie, Curiosità - Persone - Iniziative, Realizzazioni                                    |1    |
	|Economia, affari e finanza - Servizi finanziari - Servizi legali                           |1    |
	|Sport - Sci nautico                                                                        |1    |
	|Scienza, Tecnologia - Programmi aerospaziali                                               |1    |
	|Economia, affari e finanza - Informazione d'impresa - Ricerche, Sviluppo                   |1    |
	|Sociale - Abusi, Torture                                                                   |1    |
	|Arte, cultura, intrattenimento - Locali notturni                                           |1    |
	|Economia, affari e finanza - Servizi finanziari - Noleggi                                  |1    |
	|Giustizia, Criminalità - Criminalità - Droga                                               |1    |
	|Economia, affari e finanza - Macroeconomia - Mercato Cambi                                 |1    |
	|Scienza, Tecnologia - Climatologia, Meteorologia                                           |1    |
	|Politica - Diplomazia - Alleanze                                                           |1    |
	|Religioni, Fedi - Festività religiose - Natale                                             |1    |
	|Economia, affari e finanza - Produzione Chimica - Cosmetici, Prodotti per l'igiene         |1    |
	|Sport - Mini golf                                                                          |1    |
	|Sociale - Bonifica Bellica                                                                 |1    |
	|Lavoro - Legislazione Lavoro                                                               |1    |
	|Politica - Migrazioni                                                                      |1    |
	|Ambiente - Risorse naturali - Risorse Energetiche                                          |1    |
	|Scienza, Tecnologia - Scienza applicata                                                    |1    |
	|Arte, cultura, intrattenimento - Fumetti                                                   |1    |
	|Sport - Lega del rugby                                                                     |1    |
	|Politica - Politica interna - Protezione dati personali                                    |1    |
	|Economia, affari e finanza - Informatica e Telecomunicazioni - Software                    |1    |
	|Salute - Cura - Esame medico                                                               |1    |
	|Sociale - Demografia - Emigrazione                                                         |1    |
	|Ambiente - Risorse naturali - Parchi                                                       |1    |
	|Politica - Regioni                                                                         |1    |
	|Economia, affari e finanza - Costruzioni, Proprietà - Prezzo Terreni                       |1    |
	|Economia, affari e finanza - Servizi finanziari - Assicurazioni                            |1    |
	|Arte, cultura, intrattenimento - Pittura                                                   |1    |
	+-------------------------------------------------------------------------------------------+-----+