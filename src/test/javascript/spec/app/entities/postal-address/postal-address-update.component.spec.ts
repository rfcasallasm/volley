/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { PostalAddressUpdateComponent } from 'app/entities/postal-address/postal-address-update.component';
import { PostalAddressService } from 'app/entities/postal-address/postal-address.service';
import { PostalAddress } from 'app/shared/model/postal-address.model';

describe('Component Tests', () => {
    describe('PostalAddress Management Update Component', () => {
        let comp: PostalAddressUpdateComponent;
        let fixture: ComponentFixture<PostalAddressUpdateComponent>;
        let service: PostalAddressService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [PostalAddressUpdateComponent]
            })
                .overrideTemplate(PostalAddressUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PostalAddressUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PostalAddressService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PostalAddress(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.postalAddress = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PostalAddress();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.postalAddress = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
