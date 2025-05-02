import React, { useEffect, useState } from "react";
import table from "../../assets/images/table.jpg";
export default function ClientHome() {
  return (
    <div className="container-fluid">
      <div className="row mt-3">
        <div className="col-8 text-center">
          <div id="carouselExampleIndicators" class="carousel slide">
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="0"
                class="active"
                aria-current="true"
                aria-label="Slide 1"
              ></button>
              <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="https://placehold.co/300x200" alt="Mô tả ảnh" className="w-100" />
              </div>
              <div class="carousel-item">
                <img src="https://placehold.co/300x200" class="d-block w-100" alt="..." />
              </div>
              <div class="carousel-item">
                <img src="https://placehold.co/300x200" class="d-block w-100" alt="..." />
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <div className="col-4 text-center">
          <div class="card-body">
            <h5 class="card-title">Đặt bàn</h5>
          </div>
          <div class="card" className="w-100">
            <img src="https://placehold.co/300x200" class="card-img-top" alt="..." />
          </div>
        </div>
      </div>
      <div className="row mt-3">
        <div className="col-3 text-center">
          <div className="border p-5">
            <h5>Top món ăn ngon</h5>
          </div>
        </div>
        <div className="col-6 text-center">
          <div className="d-flex">
            <div className="me-2 rounded-circle w-100">
              <img src="https://placehold.co/300x200" class="rounded-circle" alt="..." />
            </div>
            <div className="me-2">
              <h5 className="card-title">Bánh mì</h5>
              <p className="fw-bold">100.000₫</p>
            </div>
            <div>
              <button className="btn btn-primary mt-2">Thêm vào giỏ hàng</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
