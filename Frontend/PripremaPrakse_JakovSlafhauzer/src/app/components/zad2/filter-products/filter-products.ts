import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product';

@Component({
  selector: 'app-filter-products',
  imports: [CommonModule, FormsModule],
  templateUrl: './filter-products.html',
  styleUrl: './filter-products.css',
})
export class FilterProducts {
  products: Product[] = [];
  searchTerm: string = '';
  selectedCategory: string = 'Sve kategorije';
  categories: string[] = [];

  minPrice?: number;
  maxPrice?: number;
  sortOption: string = 'none';

  constructor(private productService: ProductService) {
    this.products = this.productService.getProducts();
    const uniqueCategories = new Set(this.products.map(p => p.category));
    this.categories = ['Sve kategorije', ...Array.from(uniqueCategories)];
  }

  get filteredProducts(): Product[] {
    const term = this.searchTerm.trim().toLowerCase();
    const category = this.selectedCategory;
    const min = this.minPrice;
    const max = this.maxPrice;

    let result = this.products.filter(product => {
      const matchesText = !term || product.name.toLowerCase().includes(term);
      const matchesCategory =
        category === 'Sve kategorije' || product.category === category;

      const matchesMin = min == null || product.price >= min;
      const matchesMax = max == null || product.price <= max;

      return matchesText && matchesCategory && matchesMin && matchesMax;
    });

    switch (this.sortOption) {
      case 'nameAsc':
        result = result.slice().sort((a, b) => a.name.localeCompare(b.name));
        break;
      case 'nameDesc':
        result = result.slice().sort((a, b) => b.name.localeCompare(a.name));
        break;
      case 'priceAsc':
        result = result.slice().sort((a, b) => a.price - b.price);
        break;
      case 'priceDesc':
        result = result.slice().sort((a, b) => b.price - a.price);
        break;
      default:
        // bez sortiranja
        break;
    }

    return result;
  }
}
