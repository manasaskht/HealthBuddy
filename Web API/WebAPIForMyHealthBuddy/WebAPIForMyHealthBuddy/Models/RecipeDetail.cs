//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebAPIForMyHealthBuddy.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class RecipeDetail
    {
        public int RecipeDetailID { get; set; }
        public string RecipeDescription { get; set; }
        public string IngredientIds { get; set; }
        public int RecipeID { get; set; }
    
        public virtual Recipe Recipe { get; set; }
    }
}
