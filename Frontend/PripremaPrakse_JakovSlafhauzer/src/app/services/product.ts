import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  products: Product[] = [
    { name: 'Jabuka', category: 'Voće', price: 1.2 },
    { name: 'Banana', category: 'Voće', price: 0.8 },
    { name: 'Mrkva', category: 'Povrće', price: 0.7 },
    { name: 'Brokula', category: 'Povrće', price: 1.5 },
    { name: 'Piletina', category: 'Meso', price: 5.0 },
    { name: 'Janjetina', category: 'Meso', price: 7.0 },
  ];
  
  getProducts(): Product[] {
    return this.products;
  }
}
