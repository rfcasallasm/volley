/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { MembershipCardUpdateComponent } from 'app/entities/membership-card/membership-card-update.component';
import { MembershipCardService } from 'app/entities/membership-card/membership-card.service';
import { MembershipCard } from 'app/shared/model/membership-card.model';

describe('Component Tests', () => {
    describe('MembershipCard Management Update Component', () => {
        let comp: MembershipCardUpdateComponent;
        let fixture: ComponentFixture<MembershipCardUpdateComponent>;
        let service: MembershipCardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [MembershipCardUpdateComponent]
            })
                .overrideTemplate(MembershipCardUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MembershipCardUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembershipCardService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembershipCard(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membershipCard = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembershipCard();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membershipCard = entity;
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
