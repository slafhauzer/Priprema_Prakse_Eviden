import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Counter } from './components/zad1/counter/counter';
import { FilterProducts } from './components/zad2/filter-products/filter-products';
import { UserManagement } from './components/zad5/user-management/user-management';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'counter', component: Counter },
    { path: 'filter-products', component: FilterProducts },
    { path: 'user-management', component: UserManagement }
];

