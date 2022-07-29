import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LibraryGameDetailComponent } from './library-game-detail.component';

describe('LibraryGameDetailComponent', () => {
  let component: LibraryGameDetailComponent;
  let fixture: ComponentFixture<LibraryGameDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LibraryGameDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LibraryGameDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
