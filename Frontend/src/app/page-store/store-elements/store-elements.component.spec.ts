import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreElementsComponent } from './store-elements.component';

describe('StoreElementsComponent', () => {
  let component: StoreElementsComponent;
  let fixture: ComponentFixture<StoreElementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoreElementsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoreElementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
