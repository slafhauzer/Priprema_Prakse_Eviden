import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user';

@Component({
  selector: 'app-user-management',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-management.html',
  styleUrl: './user-management.css'
})
export class UserManagement implements OnInit {
  users: User[] = [];
  userForm!: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUsers();
  }

  private initForm(): void {
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['']
    });
  }

  private loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.userService.fetchUsers().subscribe({
      next: (usersFromApi) => {
        this.userService.setUsers(usersFromApi);
        this.users = this.userService.getLocalUsers(); 
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri dohvaćanju korisnika.';
        this.isLoading = false;
      }
    });
  }

  get name() {
    return this.userForm.get('name');
  }

  get email() {
    return this.userForm.get('email');
  }

  get phone() {
    return this.userForm.get('phone');
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }

    const formValue = this.userForm.value;

    const newUser: User = {
      id: this.generateNextId(),
      name: formValue.name,
      email: formValue.email,
      phone: formValue.phone || ''
    };

    // dodaj u servis (lokalnu listu)
    this.userService.addUser(newUser);

    // budući da this.users pokazuje na istu listu, tablica se automatski osvježi
    this.userForm.reset();
  }

  private generateNextId(): number {
    if (!this.users || this.users.length === 0) {
      return 1;
    }

    const maxId = Math.max(...this.users.map(u => u.id ?? 0));
    return maxId + 1;
  }
}
