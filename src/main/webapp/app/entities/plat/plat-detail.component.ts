import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Plat } from './plat.model';
import { PlatService } from './plat.service';

@Component({
    selector: 'jhi-plat-detail',
    templateUrl: './plat-detail.component.html'
})
export class PlatDetailComponent implements OnInit, OnDestroy {

    plat: Plat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private platService: PlatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlats();
    }

    load(id) {
        this.platService.find(id)
            .subscribe((platResponse: HttpResponse<Plat>) => {
                this.plat = platResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'platListModification',
            (response) => this.load(this.plat.id)
        );
    }
}
