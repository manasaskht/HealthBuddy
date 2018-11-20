using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebAPIForMyHealthBuddy.UserModels;

namespace WebAPIForMyHealthBuddy.Controllers
{
    public class GroceryController : ApiController
    {
        public List<GroceryMaster> Get(string groceryKeyword)
        {
            List<GroceryMaster> groceries = null;
            if (!String.IsNullOrWhiteSpace(groceryKeyword))
            {
                using (Models.MyHealthBuddyEntities dbContext = new Models.MyHealthBuddyEntities())
                {
                    groceries = dbContext.GroceryMasters.Where(x => x.GroceryName.StartsWith(groceryKeyword)).Select(groceryMaster => new GroceryMaster() { BarcodeId = groceryMaster.BarcodeID, GroceryName = groceryMaster.GroceryName }).ToList();
                    if (groceries == null || groceries.Count <= 0)
                        groceries = dbContext.GroceryMasters.Where(x => x.GroceryName.Contains(groceryKeyword)).Select(groceryMaster => new GroceryMaster() { BarcodeId = groceryMaster.BarcodeID, GroceryName = groceryMaster.GroceryName }).ToList();
                }
            }
            return groceries;
        }

        [HttpPost]
        [Route("api/Grocery/{newGrocery}")]
        public int AddGrocery(Grocery grocery)
        {
            Models.Grocery newGrocery = new Models.Grocery()
            {
                GroceryName = grocery.GroceryName,
                ExpiryDate = grocery.ExpiryDate,
                Calories = grocery.Calories,
                Quantity = grocery.Quantity,
                UserID = grocery.UserID,
                GroceryMasterID = grocery.GroceryMasterID
            };
            using (Models.MyHealthBuddyEntities dbContext = new Models.MyHealthBuddyEntities())
            {
                dbContext.Groceries.Add(newGrocery);
                dbContext.SaveChanges();
                return newGrocery.GroceryID;
            }

        }

        [HttpPut]
        [Route("api/Grocery/{EditGrocery}")]
        public HttpResponseMessage UpdateGrocery(Grocery grocery)
        {
            
            using (Models.MyHealthBuddyEntities dbContext = new Models.MyHealthBuddyEntities())
            {
                Models.Grocery newGrocery = dbContext.Groceries.SingleOrDefault(item => item.GroceryID == grocery.GroceryID);
                if (newGrocery != null)
                {
                    newGrocery.GroceryName = grocery.GroceryName;
                    newGrocery.ExpiryDate = grocery.ExpiryDate;
                    newGrocery.Calories = grocery.Calories;
                    newGrocery.Quantity = grocery.Quantity;
                    newGrocery.UserID = grocery.UserID;
                    newGrocery.GroceryMasterID = grocery.GroceryMasterID;
                    dbContext.Entry(newGrocery).State = System.Data.Entity.EntityState.Modified;
                    try
                    {
                        dbContext.SaveChanges();
                        return Request.CreateResponse<string>(HttpStatusCode.OK, "Update Success!");
                    }
                    catch (Exception e)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.PreconditionFailed, e);
                    }
                }
                else
                    return Request.CreateErrorResponse(HttpStatusCode.PreconditionFailed, "Record mismatch while trying to update!");
            }
        }

        [HttpDelete]
        [Route("api/Grocery/{groceryID}")]
        public HttpResponseMessage DeleteGrocery(int groceryID)
        {
            using (Models.MyHealthBuddyEntities dbContext = new Models.MyHealthBuddyEntities())
            {
                Models.Grocery groceryToDelete = dbContext.Groceries.SingleOrDefault(item => item.GroceryID == groceryID);
                if (groceryToDelete != null)
                {
                    dbContext.Groceries.Remove(groceryToDelete);
                    dbContext.SaveChanges();
                    return Request.CreateResponse<string>(HttpStatusCode.OK, "Delete Success!");
                }
                else
                    return Request.CreateErrorResponse(HttpStatusCode.PreconditionFailed, "Record not found while trying to delete!");
            }
        }

    }
}
