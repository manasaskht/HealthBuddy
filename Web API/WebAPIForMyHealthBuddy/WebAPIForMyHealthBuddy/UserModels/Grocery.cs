using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebAPIForMyHealthBuddy.UserModels
{
    public class Grocery
    {
        public int GroceryID { get; set; }
        public string GroceryName { get; set; }
        public decimal? Calories { get; set; }
        public DateTime? ExpiryDate { get; set; }
        public int? Quantity { get; set; }
        public int GroceryMasterID { get; set; }
        public int UserID { get; set; }

    }
}