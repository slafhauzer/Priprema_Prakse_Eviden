import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'https://jsonplaceholder.typicode.com/users';

  private users: User[] = [];

  constructor(private http: HttpClient) {}

  fetchUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  setUsers(users: User[]): void {
    this.users = users;
  }

  getLocalUsers(): User[] {
    return this.users;
  }

  addUser(user: User): void {
    this.users.push(user);
  }
}
